package com.example.edemo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class AddCryptoFragment extends Fragment {

    private EditText cryptoName, cryptoPrice, cryptoSize;
    private Button cryptoDate, saveButton;
    private String selectedDate;
    private DatabaseHelper databaseHelper;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_crypto, container, false);

        cryptoName = view.findViewById(R.id.cryptoName);
        cryptoPrice = view.findViewById(R.id.cryptoPrice);
        cryptoSize = view.findViewById(R.id.cryptoSize);
        cryptoDate = view.findViewById(R.id.cryptoDate);
        saveButton = view.findViewById(R.id.saveButon);

        databaseHelper = new DatabaseHelper(getContext());

        cryptoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCryptoInfo();
            }
        });

        return view;
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                cryptoDate.setText(selectedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveCryptoInfo() {
        String name = cryptoName.getText().toString().trim();
        String price = cryptoPrice.getText().toString().trim();
        String size = cryptoSize.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty() || size.isEmpty() || selectedDate == null) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            boolean isInserted = databaseHelper.insertCryptoData(name, price, size, selectedDate);
            if (isInserted) {
                Toast.makeText(getContext(), "Crypto Info Saved", Toast.LENGTH_SHORT).show();
                cryptoName.setText("");
                cryptoPrice.setText("");
                cryptoSize.setText("");
                cryptoDate.setText("Select Date");
            } else {
                Toast.makeText(getContext(), "Failed to Save Crypto Info", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

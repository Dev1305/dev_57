package com.example.edemo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class UpdateCryptoActivity extends AppCompatActivity {

    private EditText updateCryptoName, updateCryptoPrice, updateCryptoSize;
    private Button updateCryptoDate, updateButton;
    private String selectedDate;
    private DatabaseHelper databaseHelper;
    private int cryptoId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crypto);

        updateCryptoName = findViewById(R.id.updateCryptoName);
        updateCryptoPrice = findViewById(R.id.updateCryptoPrice);
        updateCryptoSize = findViewById(R.id.updateCryptoSize);
        updateCryptoDate = findViewById(R.id.updateCryptoDate);
        updateButton = findViewById(R.id.updateButton);

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        cryptoId = intent.getIntExtra("crypto_id", -1);

        if (cryptoId != -1) {
            Crypto crypto = databaseHelper.getCryptoById(cryptoId);

            if (crypto != null) {
                updateCryptoName.setText(crypto.getName());
                updateCryptoPrice.setText(crypto.getPrice());
                updateCryptoSize.setText(crypto.getSize());
                selectedDate = crypto.getDate();
                updateCryptoDate.setText(selectedDate);
            }
        }

        updateCryptoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCryptoInfo();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                updateCryptoDate.setText(selectedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void updateCryptoInfo() {
        String name = updateCryptoName.getText().toString().trim();
        String price = updateCryptoPrice.getText().toString().trim();
        String size = updateCryptoSize.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty() || size.isEmpty() || selectedDate == null) {
            Toast.makeText(UpdateCryptoActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            boolean isUpdated = databaseHelper.updateCryptoData(cryptoId, name, price, size, selectedDate);
            if (isUpdated) {
                Toast.makeText(UpdateCryptoActivity.this, "Crypto Info Updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UpdateCryptoActivity.this, "Failed to Update Crypto Info", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

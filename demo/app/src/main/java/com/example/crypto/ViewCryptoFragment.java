package com.example.edemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ViewCryptoFragment extends Fragment {

    private RecyclerView recyclerView;
    private CryptoAdapter cryptoAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<Crypto> cryptoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_crypto, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        databaseHelper = new DatabaseHelper(getContext());
        cryptoList = new ArrayList<>();

        loadCryptoData();

        cryptoAdapter = new CryptoAdapter(cryptoList, getContext(), databaseHelper);
        recyclerView.setAdapter(cryptoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void loadCryptoData() {
        cryptoList.clear();
        cryptoList.addAll(databaseHelper.getAllCryptoData());
        if (cryptoAdapter != null) {
            cryptoAdapter.notifyDataSetChanged();
        }
    }
}

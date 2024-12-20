package com.example.edemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CryptoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Crypto> cryptoList;
    private Context context;
    private DatabaseHelper databaseHelper;

    // View types
    private static final int VIEW_TYPE_CRYPTO = 0;

    public CryptoAdapter(ArrayList<Crypto> cryptoList, Context context, DatabaseHelper databaseHelper) {
        this.cryptoList = cryptoList;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CRYPTO) {
            View view = LayoutInflater.from(context).inflate(R.layout.crypto_item, parent, false);
            return new CryptoViewHolder(view);
        }
        // Handle other view types here if needed
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CryptoViewHolder) {
            Crypto crypto = cryptoList.get(position);
            CryptoViewHolder cryptoViewHolder = (CryptoViewHolder) holder;
            cryptoViewHolder.cryptoName.setText(crypto.getName());
            cryptoViewHolder.cryptoPrice.setText(crypto.getPrice());
            cryptoViewHolder.cryptoSize.setText(crypto.getSize());
            cryptoViewHolder.cryptoDate.setText(crypto.getDate());

            // Rest of your logic for CryptoViewHolder
        }
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Return the view type based on your logic (e.g., VIEW_TYPE_CRYPTO)
        return VIEW_TYPE_CRYPTO;
    }

    public static class CryptoViewHolder extends RecyclerView.ViewHolder {

        TextView cryptoName, cryptoPrice, cryptoSize, cryptoDate;

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
            cryptoName = itemView.findViewById(R.id.cryptoName);
            cryptoPrice = itemView.findViewById(R.id.cryptoPrice);
            cryptoSize = itemView.findViewById(R.id.cryptoSize);
            cryptoDate = itemView.findViewById(R.id.cryptoDate);
        }
    }
}

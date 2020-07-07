package com.example.retrofitcoinsjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitcoinsjava.R;
import com.example.retrofitcoinsjava.model.CryptoModel;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.RowHolder> {

    private ArrayList<CryptoModel> cryptoList;
    private String[] colors = {"#a3ff00","#ff00aa","#b4a7d6","#8ee5ee",
            "#cd950c","#f5f5f5","f47932"};

    public recyclerViewAdapter(ArrayList<CryptoModel> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    //Row layout ile bağlamayı yapıyoruz.
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    //Görünümlerimizi  RowHolder da bağlayıp  işlemlerini yaptığımız yer.
    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {

        holder.bind(cryptoList.get(position),colors,position);

    }

    //Kaç tane Row Oluşturulacak onu yapıyoruz
    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textCurrency;
        TextView textPrice;
        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }
        public void bind(CryptoModel cryptoModel, String [] colors, Integer position){

           itemView.setBackgroundColor(Color.parseColor(colors[position %8]));
            textCurrency = itemView.findViewById(R.id.textCurrency);
            textPrice = itemView.findViewById(R.id.textPrice);
            textCurrency.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price);

        }
    }
}

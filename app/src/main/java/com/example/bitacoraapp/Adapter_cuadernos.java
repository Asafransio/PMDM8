package com.example.bitacoraapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class Adapter_cuadernos extends RecyclerView.Adapter<Adapter_cuadernos.ViewHolderCuadernos> {

    LayoutInflater inflater;
    ArrayList<String> cuadernos;

    private final OnRecyclerItemListener mRecyListener;

    public Adapter_cuadernos(Context context, ArrayList<String> cuadernos, OnRecyclerItemListener recyListener) {
        this.inflater = LayoutInflater.from(context);
        this.cuadernos = cuadernos;
        this.mRecyListener = recyListener;
    }

    @NonNull
    @Override
    public ViewHolderCuadernos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.id_cuaderno, null, false);

        return new ViewHolderCuadernos(view, mRecyListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_cuadernos.ViewHolderCuadernos holder, int position) {
        holder.asignar(cuadernos.get(position));

    }


    @Override
    public int getItemCount() {

        return cuadernos.size();
    }

    public static class ViewHolderCuadernos extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView cuaderno;
        OnRecyclerItemListener recyListener;

        public ViewHolderCuadernos(@NonNull View itemView, OnRecyclerItemListener recyListener) {
            super(itemView);
            cuaderno = itemView.findViewById(R.id.textVacio);
            this.recyListener = recyListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void asignar(String s) {

            cuaderno.setText(s.split("-")[1]);

        }

        @Override
        public void onClick(View v) {
            try {
                recyListener.onItemClick(getAdapterPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            try {
                recyListener.onItemHold(getAdapterPosition());
                Log.println(Log.INFO, "INFO", "se mantiene");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    public interface OnRecyclerItemListener{
        void onItemClick(int position) throws JSONException;
        void onItemHold(int position) throws JSONException;
    }
}

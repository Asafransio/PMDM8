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

public class Adapter_apuntes extends RecyclerView.Adapter<Adapter_apuntes.ViewHolderApuntes> {

    LayoutInflater inflater;
    ArrayList<String> apuntes;

    private final OnRecyclerItemListener mRecyListener;

    public Adapter_apuntes(Context context, ArrayList<String> apuntes, OnRecyclerItemListener recyListener) {
        this.inflater = LayoutInflater.from(context);
        this.apuntes = apuntes;
        this.mRecyListener = recyListener;
    }

    @NonNull
    @Override
    public ViewHolderApuntes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.id_cuaderno, null, false);

        return new ViewHolderApuntes(view, mRecyListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderApuntes holder, int position) {
        holder.asignar(apuntes.get(position));

    }

    @Override
    public int getItemCount() {
        return apuntes.size();
    }

    public static class ViewHolderApuntes extends RecyclerView.ViewHolder implements  View.OnLongClickListener{

        TextView apunte;
        OnRecyclerItemListener recyListener;

        public ViewHolderApuntes(@NonNull View itemView, OnRecyclerItemListener recyListener) {
            super(itemView);
            apunte = itemView.findViewById(R.id.textVacio);
            this.recyListener = recyListener;
            itemView.setOnLongClickListener(this);

        }

        public void asignar(String s) {

            apunte.setText(s.split(" / ")[1] + " -- " + s.split(" / ")[2]);

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

        void onItemHold(int position) throws JSONException;
    }
}

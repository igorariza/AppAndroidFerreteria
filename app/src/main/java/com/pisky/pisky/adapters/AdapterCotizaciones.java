package com.pisky.pisky.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pisky.pisky.R;
import com.pisky.pisky.models.CotizacionesClass;
import com.pisky.pisky.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCotizaciones extends RecyclerView.Adapter<AdapterCotizaciones.ViewHolder> {


    Context context;
    private ArrayList<CotizacionesClass> cotizacionesClass = new ArrayList<>();

    public AdapterCotizaciones(Context c, ArrayList<CotizacionesClass> p) {
        context = c;
        cotizacionesClass = p;
    }

    @Override
    public AdapterCotizaciones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cotizaciones, parent, false);
        return new AdapterCotizaciones.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(AdapterCotizaciones.ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        Picasso.get()
                .load(R.drawable.heart)
                .placeholder(R.drawable.heart)
                .centerCrop()
                .error(R.drawable.heart)
                .fit()
                .priority(Picasso.Priority.HIGH)
                .transform(new CircleTransform()).into(holder.image);


        holder.name.setText(cotizacionesClass.get(position).getNombreCotizacion());
        holder.cant.setText(cotizacionesClass.get(position).getNombreCotizacion());


    }

    @Override
    public int getItemCount() {
        return cotizacionesClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView cant;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_carrera);
            name = itemView.findViewById(R.id.primary_text);
            cant = itemView.findViewById(R.id.sub_text);
        }
    }
}

package com.pisky.pisky.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pisky.pisky.CotizacionActivity;
import com.pisky.pisky.MainActivity;
import com.pisky.pisky.MainUsuario;
import com.pisky.pisky.R;
import com.pisky.pisky.adapters.AdapterCotizaciones;
import com.pisky.pisky.models.CotizacionesClass;

import java.util.ArrayList;

public class Cotizaciones extends Fragment {

    private View view;
    private TextView txt_cot;
    private AdapterCotizaciones adapterCotizaciones;
    ArrayList<CotizacionesClass> cotizacionesArray = new ArrayList<CotizacionesClass>();
    private RecyclerView cotizaciones;

    public Cotizaciones() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cotizaciones, container, false);


        vistas(view);
        return view;
    }

    private void vistas(View view) {

        FloatingActionButton fabBtn = (FloatingActionButton) view.findViewById(R.id.fab);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CotizacionActivity.class);
                startActivity(intent);
            }
        });

        GridLayoutManager layoutCotizaciones = new GridLayoutManager(getActivity(), 2);
        cotizaciones = view.findViewById(R.id.recyclerViewCotizaciones);
        cotizaciones.setLayoutManager(layoutCotizaciones);
        cotizacionesAll();
    }

    private void cotizacionesAll() {

        CotizacionesClass cotizaciones1 = new CotizacionesClass();
        cotizaciones1.setNombreCotizacion("Cotizacion1");
        cotizaciones1.setEstadoImg("Cotizacion1");
        cotizacionesArray.add(cotizaciones1);

        adapterCotizaciones = new AdapterCotizaciones(getActivity(), cotizacionesArray);
        cotizaciones.setAdapter(adapterCotizaciones);


    }
}
package com.pisky.pisky.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pisky.pisky.R;

public class Pedidos extends Fragment {

    private View view;
    public Pedidos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pedidos, container, false);
        return view;
    }
}

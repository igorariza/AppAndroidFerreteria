package com.pisky.pisky;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pisky.pisky.fragment.Cotizaciones;
import com.pisky.pisky.fragment.MiCuenta;
import com.pisky.pisky.fragment.Pedidos;

public class MainUsuario extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(R.style.HomeTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        final Fragment cotizacionesFragment = new Cotizaciones();
        final Fragment miCuentaFragment = new MiCuenta();
        final Fragment pedidosFragment = new Pedidos();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_placeholder, cotizacionesFragment).commit();
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.action_cotizar:
                        fragmentTransaction.replace(R.id.main_fragment_placeholder, cotizacionesFragment).commit();
                        break;
                    case R.id.action_pedidos:
                        fragmentTransaction.replace(R.id.main_fragment_placeholder, pedidosFragment).commit();
                        break;
                    case R.id.action_cuenta:
                        fragmentTransaction.replace(R.id.main_fragment_placeholder, miCuentaFragment).commit();
                        break;
                }

                return true;
            }
        });

    }
}

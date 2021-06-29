package com.pisky.pisky.models;

import java.io.Serializable;

public class CotizacionesClass implements Serializable {
    private String nombreCotizacion;
    private String estadoImg;

    public CotizacionesClass() {
    }

    public String getNombreCotizacion() {
        return nombreCotizacion;
    }

    public void setNombreCotizacion(String nombreCotizacion) {
        this.nombreCotizacion = nombreCotizacion;
    }

    public String getEstadoImg() {
        return estadoImg;
    }

    public void setEstadoImg(String estadoImg) {
        this.estadoImg = estadoImg;
    }
}

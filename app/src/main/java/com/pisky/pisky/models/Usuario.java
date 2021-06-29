package com.pisky.pisky.models;

import android.widget.ImageView;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static Usuario instance;

    private String idUser;
    private String diUser;
    private String nacimientoUser;
    private String generoUser;
    private String emailUser;
    private String nameUser;
    private String photoUser;
    private String phoneUser;
    private String direccionUser;
    private ImageView img_user;

    public Usuario() {
    }

    public Usuario(String idUser, String diUser, String nacimientoUser, String generoUser, String emailUser, String nameUser, String photoUser, String phoneUser, String direccionUser) {
        this.idUser = idUser;
        this.diUser = diUser;
        this.nacimientoUser = nacimientoUser;
        this.generoUser = generoUser;
        this.emailUser = emailUser;
        this.nameUser = nameUser;
        this.photoUser = photoUser;
        this.phoneUser = phoneUser;
        this.direccionUser = direccionUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDiUser() {
        return diUser;
    }

    public void setDiUser(String diUser) {
        this.diUser = diUser;
    }

    public String getNacimientoUser() {
        return nacimientoUser;
    }

    public void setNacimientoUser(String nacimientoUser) {
        this.nacimientoUser = nacimientoUser;
    }

    public String getGeneroUser() {
        return generoUser;
    }

    public void setGeneroUser(String generoUser) {
        this.generoUser = generoUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getDireccionUser() {
        return direccionUser;
    }

    public void setDireccionUser(String direccionUser) {
        this.direccionUser = direccionUser;
    }

    public ImageView getImg_user() {
        return img_user;
    }

    public void setImg_user(ImageView img_user) {
        this.img_user = img_user;
    }

    public static Usuario getInstance() {
        return instance;
    }

    public static void setInstance(Usuario instance) {
        Usuario.instance = instance;
    }


}

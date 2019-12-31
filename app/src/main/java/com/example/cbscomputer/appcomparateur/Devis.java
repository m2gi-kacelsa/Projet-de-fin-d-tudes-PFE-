package com.example.cbscomputer.appcomparateur;


public class Devis {
    private int image;
    private String pseudo;
    private String text;

    public Devis(int image, String pseudo, String text) {
        this.image = image;
        this.pseudo = pseudo;
        this.text = text;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {return image; }

    public String getPseudo() {
        return pseudo;
    }

    public String getText() {
        return text;
    }
}


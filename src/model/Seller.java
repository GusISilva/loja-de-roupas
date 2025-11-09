package model;

import java.sql.Date;

public class Seller extends UserPattern{

    private String causaSocial;
    private String tipo;
    private double notaMedia;

    public Seller(String nome, String causaSocial, String tipo, double notaMedia) {
        super(nome);
        this.causaSocial = causaSocial;
        this.tipo = tipo;
        this.notaMedia = notaMedia;
    }

    public String getCausaSocial() {
        return causaSocial;
    }

    public void setCausaSocial(String causaSocial) {
        this.causaSocial = causaSocial;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(double notaMedia) {
        this.notaMedia = notaMedia;
    }
}
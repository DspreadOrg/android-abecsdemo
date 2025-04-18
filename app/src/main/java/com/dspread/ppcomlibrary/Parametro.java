package com.dspread.ppcomlibrary;

import java.io.Serializable;


/**
 * Created by antonio.silva on 24/08/2017.
 */

public class Parametro implements Serializable {

    private String nome;
    private Tipo tipo;
    private Formato formato;
    private int tamanho;
    private String valor;

    public Parametro(String nome, Tipo tipo, String valor) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Parametro(String nome, Formato formato, int tamanho) {
        this.nome = nome;
        this.formato = formato;
        this.tamanho = tamanho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}

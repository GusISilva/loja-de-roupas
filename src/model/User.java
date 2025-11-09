package model;

import java.time.Instant;
import java.time.LocalDate;

public abstract class User extends UserPattern{
    private int idade;
    private Character sexo;
    private Instant dataNasc;

    public User(String nome, int idade, Character sexo, Instant dataNasc){
        super(nome);
        this.idade = idade;
        this.sexo = sexo;
        this.dataNasc = dataNasc;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Instant getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = Instant.from(dataNasc);
    }
}
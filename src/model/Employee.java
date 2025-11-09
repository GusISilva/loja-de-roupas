package model;

import java.math.BigDecimal;
import java.time.Instant;

public class Employee extends User{

    private Positions position;
    private BigDecimal salary;

    public Employee(String nome, int idade, char sexo, Instant dataNasc, Positions position) {
        super(nome, idade, sexo, dataNasc);
        this.position = position;
    }

    public Positions getCargo() {
        return position;
    }

    public void setCargo(Positions cargo) {
        this.position = cargo;
    }
}

package model;

import java.math.BigDecimal;
import java.time.Instant;

public class Client extends User{
    private BigDecimal cashBack;

    public Client(String nome, int idade, Character sexo, Instant dataNasc){
        super(nome, idade, sexo, dataNasc);
        this.cashBack = BigDecimal.ZERO;
    }

    public void setCashBack(BigDecimal value){
        this.cashBack = this.cashBack.add(value.multiply(BigDecimal.valueOf(0.02)));
    }

    public BigDecimal useCashBack(BigDecimal price){
        if(cashBack.compareTo(price) > 0){
            this.cashBack = this.cashBack.subtract(price);
            return BigDecimal.ZERO;
        }

        BigDecimal value = cashBack;
        this.cashBack = BigDecimal.ZERO;
        return price.subtract(value);
    }

}
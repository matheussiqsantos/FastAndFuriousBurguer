package br.dev.matheus.FastAndFuriousBurguer.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItensPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qtd;
    private double valUnit;

    public ItensPedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getValUnit() {
        return valUnit;
    }

    public void setValUnit(double valUnit) {
        this.valUnit = valUnit;
    }

    public ItensPedido(Long id, int qtd, double valUnit) {
        this.id = id;
        this.qtd = qtd;
        this.valUnit = valUnit;
    }
    
    
}

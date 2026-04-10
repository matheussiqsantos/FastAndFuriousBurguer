package br.dev.matheus.FastAndFuriousBurguer.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author sesi3dib
 */
@Entity
public class ItensPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int qtd;
    private double valUnit;

    public ItensPedido() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public ItensPedido(long id, int qtd, double valUnit) {
        this.id = id;
        this.qtd = qtd;
        this.valUnit = valUnit;
    }
    
    
}

package br.dev.matheus.FastAndFuriousBurguer.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItensPedido> itens;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome")
    private String nome;
    
    @Column(name = "preco") 
    private Double preco;

    @Column(name = "data_aberto")
    private LocalDateTime data_aberto;

    @Column(name = "data_pronto")
    private LocalDateTime data_pronto;

    @Column(name = "data_entregue")
    private LocalDateTime data_entregue;

    @Column(name = "data_cancelado")
    private LocalDateTime data_cancelado;

    public Pedido(Long id, String cpf, String nome, StatusPedido status, Double preco,
            LocalDateTime data_aberto, LocalDateTime data_pronto, LocalDateTime data_entregue, LocalDateTime data_cancelado) {

        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.status = status;
        this.preco = preco;
        this.data_aberto = data_aberto;
        this.data_pronto = data_pronto;
        this.data_entregue = data_entregue;
        this.data_cancelado = data_cancelado;
    }

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<ItensPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItensPedido> itens) {
        this.itens = itens;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDateTime getData_aberto() {
        return data_aberto;
    }

    public void setData_aberto(LocalDateTime data_aberto) {
        this.data_aberto = data_aberto;
    }

    public LocalDateTime getData_pronto() {
        return data_pronto;
    }

    public void setData_pronto(LocalDateTime data_pronto) {
        this.data_pronto = data_pronto;
    }

    public LocalDateTime getData_entregue() {
        return data_entregue;
    }

    public void setData_entregue(LocalDateTime data_entregue) {
        this.data_entregue = data_entregue;
    }

    public LocalDateTime getData_cancelado() {
        return data_cancelado;
    }

    public void setData_cancelado(LocalDateTime data_cancelado) {
        this.data_cancelado = data_cancelado;
    }

}

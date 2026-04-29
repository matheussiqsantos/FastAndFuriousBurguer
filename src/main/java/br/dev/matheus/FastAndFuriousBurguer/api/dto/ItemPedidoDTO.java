package br.dev.matheus.FastAndFuriousBurguer.api.dto;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.ItensPedido;

public class ItemPedidoDTO {
    private Long idProduto;
    private String nomeProduto;
    private int qtd;
    private Double valUnit;
    private String obs;

    public ItemPedidoDTO(ItensPedido item) {
     this.idProduto = item.getProduto().getId();
     this.nomeProduto = item.getProduto().getNome();
     this.qtd = item.getQtd();
     this.valUnit = item.getValUnit();
     this.obs = item.getObs();
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getQtd() {
        return qtd;
    }

    public Double getValUnit() {
        return valUnit;
    }

    public String getObs() {
        return obs;
    }
    
    
}

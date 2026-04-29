package br.dev.matheus.FastAndFuriousBurguer.api.dto;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.StatusPedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "nome", "cpf", "status", "data_aberto", "data_pronto", "data_entregue", "data_cancelado", "itens" })
public class PedidoDTO {
    
    private Long id;
    private String cpf;
    private String nome;
    private StatusPedido status;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime data_aberto;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime data_pronto;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime data_entregue;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime data_cancelado;
    
    private List<ItemPedidoDTO> itens;
    
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cpf = pedido.getCpf();
        this.nome = pedido.getNome();
        this.status = pedido.getStatus();
        this.data_aberto = pedido.getData_aberto();
        this.data_pronto = pedido.getData_pronto();
        this.data_entregue = pedido.getData_entregue();
        this.data_cancelado = pedido.getData_cancelado();
        this.itens = pedido.getItens().stream()
                .map(ItemPedidoDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public LocalDateTime getData_aberto() {
        return data_aberto;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public LocalDateTime getData_pronto() {
        return data_pronto;
    }

    public LocalDateTime getData_entregue() {
        return data_entregue;
    }

    public LocalDateTime getData_cancelado() {
        return data_cancelado;
    }
    
}

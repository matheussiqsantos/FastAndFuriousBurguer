package br.dev.matheus.FastAndFuriousBurguer.domain.service;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.StatusPedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public Pedido emitir(Pedido pedido) {

        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setData_aberto(LocalDateTime.now());

        if (pedido.getItens() != null) {
            pedido.getItens().forEach(item -> {

                if (item.getProduto() == null || item.getProduto().getId() == null) {
                    throw new RuntimeException("É necessário informar o ID do produto para cada item.");
                }

                Produto produto = produtoRepository.findById(item.getProduto().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                item.setPedido(pedido);
                item.setProduto(produto);
                item.setValUnit(produto.getPreco());
            });
        }

        return pedidoRepository.save(pedido);
    }
}

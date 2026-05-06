package br.dev.matheus.FastAndFuriousBurguer.domain.service;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.StatusPedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        final double[] totalAcumulado = {0.0}; // Usei um array de uma posição para fazer o cálculo do valor do pedido

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

                totalAcumulado[0] += (item.getValUnit() * item.getQtd());
            });
        }

        pedido.setPreco(totalAcumulado[0]);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizar(Long id, Pedido dadosNovos) {

        Pedido pedidoAtual = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (dadosNovos.getCpf() != null) {
            pedidoAtual.setCpf(dadosNovos.getCpf());
        }

        if (dadosNovos.getNome() != null) {
            pedidoAtual.setNome(dadosNovos.getNome());
        }

        if (dadosNovos.getStatus() != null) {
            pedidoAtual.setStatus(dadosNovos.getStatus());
        }

        if (dadosNovos.getItens() != null && !dadosNovos.getItens().isEmpty()) {

            pedidoAtual.getItens().clear();

            dadosNovos.getItens().forEach(itemNovo -> {

                if (itemNovo.getProduto() == null || itemNovo.getProduto().getId() == null) {
                    throw new RuntimeException("É necessário informar o ID do produto para cada item.");
                }

                Produto produto = produtoRepository.findById(itemNovo.getProduto().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado ID: " + itemNovo.getProduto().getId()));

                itemNovo.setPedido(pedidoAtual);
                itemNovo.setProduto(produto);
                itemNovo.setValUnit(produto.getPreco());

                pedidoAtual.getItens().add(itemNovo);
            });
        }

        // 4. Salva o pedido com as alterações parciais ou totais
        return pedidoRepository.save(pedidoAtual);
    }

    @Transactional
    public Pedido atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido statusPedidoAtt = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        statusPedidoAtt.setStatus(novoStatus);

        if (novoStatus == StatusPedido.PRONTO) {
            statusPedidoAtt.setData_pronto(LocalDateTime.now());
        } else if (novoStatus == StatusPedido.ENTREGUE) {
            statusPedidoAtt.setData_entregue(LocalDateTime.now());
        } else if (novoStatus == StatusPedido.CANCELADO) {
            statusPedidoAtt.setData_cancelado(LocalDateTime.now());
        }

        return pedidoRepository.save(statusPedidoAtt);
    }

    @Transactional
    public void excluir(Long id) {

        if (!pedidoRepository.existsById(id)) {

            throw new RuntimeException("Pedido não encontrado para exclusão");
        }

        pedidoRepository.deleteById(id);
    }
}

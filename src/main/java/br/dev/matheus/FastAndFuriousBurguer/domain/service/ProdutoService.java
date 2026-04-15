package br.dev.matheus.FastAndFuriousBurguer.domain.service;

import br.dev.matheus.FastAndFuriousBurguer.domain.exception.DomainException;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto atualizar(Long id, Produto dadosNovos) {
        Produto produtoAtual = produtoRepository.findById(id)
                .orElseThrow(() -> new DomainException("Produto não encontrado"));

        if (dadosNovos.getNome() != null) {
            Optional<Produto> produtoExistente = produtoRepository.findByNome(dadosNovos.getNome());

            // A verificação e o setNome devem ficar dentro deste IF
            if (produtoExistente.isPresent() && !produtoExistente.get().getId().equals(id)) {
                throw new DomainException("Já existe um produto com esse nome");
            }

            produtoAtual.setNome(dadosNovos.getNome());
        }

        if (dadosNovos.getPreco() != null) {
            produtoAtual.setPreco(dadosNovos.getPreco());
        }

        if (dadosNovos.getIngredientes() != null) {
            produtoAtual.setIngredientes(dadosNovos.getIngredientes());
        }

        if (dadosNovos.getCategoria() != null) {
            produtoAtual.setCategoria(dadosNovos.getCategoria());
        }

        return produtoRepository.save(produtoAtual);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }
}

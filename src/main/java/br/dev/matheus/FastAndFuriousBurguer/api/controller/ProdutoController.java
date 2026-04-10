
package br.dev.matheus.FastAndFuriousBurguer.api.controller;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fastfurious")
public class ProdutoController 
{
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @GetMapping("/produtos")
    public List<Produto> listas()
    {
        return produtoRepository.findAll();
    }
}

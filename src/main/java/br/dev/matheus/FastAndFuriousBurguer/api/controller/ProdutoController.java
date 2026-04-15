
package br.dev.matheus.FastAndFuriousBurguer.api.controller;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import br.dev.matheus.FastAndFuriousBurguer.domain.service.ProdutoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fastfurious")
public class ProdutoController 
{
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping("/produtos")
    public List<Produto> lista()
    {
        return produtoRepository.findAll();
        // return produtoRepository.findByNomeContaining("X-Burguer");
    }
    
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> listaid(
                            @PathVariable Long id)
    {
               Optional<Produto> produto = produtoRepository.findById(id);
       
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@RequestBody Produto produto) 
    {
        return produtoRepository.save(produto);
    }
    
    @PutMapping("/produtos/{id}")
    public ResponseEntity<Produto> atualizar(
                                @Valid @PathVariable Long id,
                                @RequestBody Produto produto) {
        
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } 
        
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        
        return ResponseEntity.ok(produtoAtualizado);
    }
    
    @DeleteMapping("produtos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        
        if(!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    
    
    
    
}

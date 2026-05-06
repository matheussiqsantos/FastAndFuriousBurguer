
package br.dev.matheus.FastAndFuriousBurguer.api.controller;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.CategoriaProduto;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import br.dev.matheus.FastAndFuriousBurguer.domain.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Produtos", description = "Gerenciamento de produtos do cardápio")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados no banco de dados")
    @GetMapping("/produtos")
    public List<Produto> lista() {
        return produtoRepository.findAll();
    }

    @Operation(summary = "Busca um produto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> listaid(
            @Parameter(description = "ID do produto a ser pesquisado", example = "1") 
            @PathVariable Long id) {
        
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Lista produtos por categoria")
    @GetMapping("produtos/categoria/{categoria}")
    public List<Produto> listarcat(
            @Parameter(description = "Categoria do produto", example = "LANCHE")
            @PathVariable CategoriaProduto categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    @Operation(summary = "Cadastra um novo produto")
    @PostMapping("/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @Operation(summary = "Atualiza um produto existente")
    @PutMapping("/produtos/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long id,
            @RequestBody Produto produto) {
        
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } 
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Exclui um produto")
    @DeleteMapping("produtos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
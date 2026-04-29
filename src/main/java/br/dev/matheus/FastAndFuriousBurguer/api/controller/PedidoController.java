
package br.dev.matheus.FastAndFuriousBurguer.api.controller;

import br.dev.matheus.FastAndFuriousBurguer.api.dto.PedidoDTO;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.StatusPedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import br.dev.matheus.FastAndFuriousBurguer.domain.service.PedidoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
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
public class PedidoController {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired 
    private PedidoService pedidoService;
    
    @GetMapping("/pedidos")
    public List<PedidoDTO> lista() 
    {
        return pedidoRepository.findAll().stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoDTO> listaid(@PathVariable Long id) {

         return pedidoRepository.findById(id)
                 .map(pedido -> ResponseEntity.ok(new PedidoDTO(pedido)))
                 .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/pedidos/status/{status}")
    public List<PedidoDTO> listaid(@PathVariable StatusPedido status) {
        List<Pedido> pedidos = pedidoRepository.findByStatus(status);
        
        return pedidos.stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList())
;        
    }
    
    @PostMapping("/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@RequestBody Pedido pedido) {
        
        Pedido pedidoSalvo = pedidoService.emitir(pedido);
        
        return new PedidoDTO(pedidoSalvo);
    }
    
    @PutMapping("/pedidos/{id}") 
    public ResponseEntity<PedidoDTO> atualizar(
                                @Valid @PathVariable Long id,
                                @RequestBody Pedido pedido) {
        
        Pedido pedidoAtualizado = pedidoService.atualizar(id, pedido);
        
        return ResponseEntity.ok(new PedidoDTO(pedidoAtualizado));
    }
    
    @PutMapping("pedidos/status/{id}")
    public ResponseEntity<PedidoDTO> atualizarstatus(
                                @PathVariable Long id,
                                @RequestBody Pedido novoStatus) {
        
        Pedido statusAtualizado = pedidoService.atualizarStatus(id, novoStatus.getStatus());
        
        return ResponseEntity.ok(new PedidoDTO(statusAtualizado));
    }
    
    @DeleteMapping("pedidos/{id}")
    public ResponseEntity<Void> excluir(@Valid @PathVariable Long id) {
   
        
        pedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

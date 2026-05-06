
package br.dev.matheus.FastAndFuriousBurguer.api.controller;

import br.dev.matheus.FastAndFuriousBurguer.api.dto.PedidoDTO;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.StatusPedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import br.dev.matheus.FastAndFuriousBurguer.domain.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos e fluxos de status")
public class PedidoController {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired 
    private PedidoService pedidoService;
    
    @Operation(summary = "Lista todos os pedidos", description = "Retorna uma lista de pedidos formatada em DTO")
    @GetMapping("/pedidos")
    public List<PedidoDTO> lista() {
        return pedidoRepository.findAll().stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Busca pedido por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoDTO> listaid(@Parameter(description = "ID do pedido") @PathVariable Long id) {
         return pedidoRepository.findById(id)
                 .map(pedido -> ResponseEntity.ok(new PedidoDTO(pedido)))
                 .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Filtra pedidos por Status", description = "Ex: ABERTO, PRONTO, ENTREGUE")
    @GetMapping("/pedidos/status/{status}")
    public List<PedidoDTO> listaid(@Parameter(description = "Status do pedido") @PathVariable StatusPedido status) {
        List<Pedido> pedidos = pedidoRepository.findByStatus(status);
        return pedidos.stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Emite um novo pedido", description = "Recebe um objeto Pedido e retorna o DTO salvo")
    @PostMapping("/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@RequestBody Pedido pedido) {
        Pedido pedidoSalvo = pedidoService.emitir(pedido);
        return new PedidoDTO(pedidoSalvo);
    }

    @Operation(summary = "Atualiza dados gerais do pedido")
    @PutMapping("/pedidos/{id}") 
    public ResponseEntity<PedidoDTO> atualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoAtualizado = pedidoService.atualizar(id, pedido);
        return ResponseEntity.ok(new PedidoDTO(pedidoAtualizado));
    }

    @Operation(summary = "Atualiza apenas o status do pedido", description = "Altera o fluxo do pedido (ex: de ABERTO para PRONTO)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Transição de status inválida")
    })
    @PutMapping("pedidos/status/{id}")
    public ResponseEntity<PedidoDTO> atualizarstatus(
            @PathVariable Long id, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto contendo o novo status") 
            @RequestBody Pedido novoStatus) {
        
        Pedido statusAtualizado = pedidoService.atualizarStatus(id, novoStatus.getStatus());
        return ResponseEntity.ok(new PedidoDTO(statusAtualizado));
    }

    @Operation(summary = "Exclui um pedido permanentemente")
    @DeleteMapping("pedidos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

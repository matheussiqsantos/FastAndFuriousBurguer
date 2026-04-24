
package br.dev.matheus.FastAndFuriousBurguer.api.controller;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import br.dev.matheus.FastAndFuriousBurguer.domain.service.PedidoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<Pedido> lista() 
    {
        return pedidoRepository.findAll();
    }
    
    @PostMapping("/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido adicionar(@RequestBody Pedido pedido) {
        
        return pedidoService.emitir(pedido);
    }
    
}

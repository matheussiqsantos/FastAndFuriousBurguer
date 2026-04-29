package br.dev.matheus.FastAndFuriousBurguer.domain.repository;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.matheus.FastAndFuriousBurguer.domain.model.StatusPedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatus(StatusPedido status);
    
}

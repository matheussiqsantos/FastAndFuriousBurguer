package br.dev.matheus.FastAndFuriousBurguer.domain.repository;

import br.dev.matheus.FastAndFuriousBurguer.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    
}

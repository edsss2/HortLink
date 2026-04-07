package com.devf.hortilink.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devf.hortilink.dto.ProdutoMaisVendido;
import com.devf.hortilink.dto.VendasPorDia;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.enums.StatusPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    long countByVendedorIdAndStatus(Long vendedorId, StatusPedido status);

    @Query("SELECT SUM(p.valorTotal), COUNT(p) FROM Pedido p " +
           "WHERE p.vendedor.id = :vendedorId " +
           "AND p.status = 'ENTREGUE' " +
           "AND p.dataPedido >= :inicioMes")
    Object[] findResumoMensal(Long vendedorId, LocalDateTime inicioMes);
    
    @Query("""
    	       SELECT CAST(p.dataPedido AS date) AS data, 
    	              SUM(p.valorTotal) AS total 
    	       FROM Pedido p 
    	       WHERE p.vendedor.id = :vendedorId 
    	       AND p.dataPedido >= :seteDiasAtras 
    	       GROUP BY data 
    	       ORDER BY data ASC
    	       """)
    	List<VendasPorDia> findVendasUltimos7Dias(Long vendedorId, LocalDateTime seteDiasAtras);

    @Query("""
    	       SELECT i.oferta.produto.nome AS nomeProduto, 
    	              SUM(i.quantidade) AS quantidadeVendida 
    	       FROM ItemPedido i 
    	       WHERE i.pedido.vendedor.id = :vendedorId 
    	       GROUP BY nomeProduto 
    	       ORDER BY quantidadeVendida DESC
    	       """)
    	List<ProdutoMaisVendido> findTop5Produtos(Long vendedorId, Pageable pageable);
}

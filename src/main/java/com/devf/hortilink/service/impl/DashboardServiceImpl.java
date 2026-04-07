package com.devf.hortilink.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devf.hortilink.dto.DashboardDTO;
import com.devf.hortilink.dto.ProdutoMaisVendido;
import com.devf.hortilink.dto.VendasPorDia;
import com.devf.hortilink.enums.StatusPedido;
import com.devf.hortilink.repository.PedidoRepository;
import com.devf.hortilink.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private PedidoRepository repository;
	
	@Override
	public DashboardDTO obterDadosDashboard(Long vendedorId) {
		LocalDate agora = LocalDate.now();
        LocalDateTime inicioMes = agora.withDayOfMonth(1).atStartOfDay();
        LocalDateTime seteDiasAtras = agora.minusDays(7).atStartOfDay();
        
        long pendentes = repository.countByVendedorIdAndStatus(vendedorId, StatusPedido.PENDENTE);
        
        Object[] resumo = (Object[]) repository.findResumoMensal(vendedorId, inicioMes);
        BigDecimal faturamento = (resumo[0] != null) ? (BigDecimal) resumo[0] : BigDecimal.ZERO;
        Long totalPedidos = (resumo[1] != null) ? (Long) resumo[1] : 0L;

        // 3. Ticket Médio (Faturamento / Total Pedidos)
        BigDecimal ticketMedio = totalPedidos > 0 
            ? faturamento.divide(BigDecimal.valueOf(totalPedidos), RoundingMode.HALF_UP) 
            : BigDecimal.ZERO;

        List<VendasPorDia> vendasSemana = repository.findVendasUltimos7Dias(vendedorId, seteDiasAtras);
        List<ProdutoMaisVendido> top5 = repository.findTop5Produtos(vendedorId, PageRequest.of(0, 5));

        return new DashboardDTO(
            faturamento,
            totalPedidos,
            pendentes,
            ticketMedio,
            vendasSemana,
            top5
        );

	}

}

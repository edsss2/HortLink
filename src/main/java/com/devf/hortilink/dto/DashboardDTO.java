package com.devf.hortilink.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardDTO(
	    BigDecimal faturamentoMes,
	    Long pedidosMes,
	    Long pedidosPendentes,
	    BigDecimal ticketMedio,
	    List<VendasPorDia> vendasUltimos7Dias,
	    List<ProdutoMaisVendido> top5Produtos
	) {}

package com.devf.hortilink.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VendasPorDia {

	LocalDate getData();
	BigDecimal getTotal();
}

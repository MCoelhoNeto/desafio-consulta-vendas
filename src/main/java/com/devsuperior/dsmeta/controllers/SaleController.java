package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(@RequestParam(name = "minDate", required = false, defaultValue = "") String minDate,
													  @RequestParam(name = "maxDate", required = false, defaultValue = "") String maxDate,
													  @RequestParam(name = "name", required = false, defaultValue = "") String name,
													  Pageable pageable) {

		// [OUT] dataFinal: se não informada -> hoje (zona do sistema)
		LocalDate dataFinal;
		if (maxDate == null || maxDate.isBlank()) {
			dataFinal = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			dataFinal = LocalDate.parse(maxDate); // espera ISO: YYYY-MM-DD
		}

		// [OUT] dataInicial: se não informada -> 1 ano antes de dataFinal
		LocalDate dataInicial;
		if (minDate == null || minDate.isBlank()) {
			dataInicial = dataFinal.minusYears(1L);
		} else {
			dataInicial = LocalDate.parse(minDate);
		}

		Page<SaleMinDTO> saleMinDto = service.getReport(pageable, dataInicial, dataFinal, name);
		return ResponseEntity.ok(saleMinDto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleMinSummaryDTO>> getSummary(
			@RequestParam(name = "minDate", required = false, defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", required = false, defaultValue = "") String maxDate) {


		// [OUT] dataFinal: se não informada -> hoje (zona do sistema)
		LocalDate dataFinal;
		if (maxDate == null || maxDate.isBlank()) {
			dataFinal = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			dataFinal = LocalDate.parse(maxDate); // espera ISO: YYYY-MM-DD
		}

		// [OUT] dataInicial: se não informada -> 1 ano antes de dataFinal
		LocalDate dataInicial;
		if (minDate == null || minDate.isBlank()) {
			dataInicial = dataFinal.minusYears(1L);
		} else {
			dataInicial = LocalDate.parse(minDate);
		}

		List<SaleMinSummaryDTO> saleMinSummaryDTO = service.getSummary(dataInicial, dataFinal);
		return ResponseEntity.ok(saleMinSummaryDTO);
	}
}

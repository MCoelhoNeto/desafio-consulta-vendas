package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleMinSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;


	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SaleMinDTO> getReport(Pageable pageable,
									  LocalDate dataInicial ,
									  LocalDate dataFinal,
									  String name) {

		Page<SaleMinDTO> result = repository.getReport(
				 dataInicial, dataFinal, name, pageable);

		return result;
	}

	@Transactional(readOnly = true)
	public List<SaleMinSummaryDTO> getSummary(LocalDate dataInicial,
											  LocalDate dataFinal) {
		List<SaleMinSummaryDTO> result = repository.getSummary(dataInicial,dataFinal );
		return result;
	}
}

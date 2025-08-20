package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.entities.Sale;

public class SaleMinSummaryDTO {

    private String sellerName;
    private Double total;


    public SaleMinSummaryDTO(Double  total, String sellerName) {
        this.sellerName = sellerName;
        this.total = total;

    }

    public SaleMinSummaryDTO(Long id, Double  total, LocalDate date, String sellerName) {
        this.total = total;
        this.sellerName = sellerName;
    }

    public SaleMinSummaryDTO(Sale entity) {
        total = entity.getAmount();
        sellerName = entity.getSeller().getName();
    }

    public Double  getTotal() {
        return total;
    }


    public String getSellerName() {
        return sellerName;
    }
}
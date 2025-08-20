package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

      @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.amount, obj.date, obj.seller.name) "
            +"FROM Sale obj "
            + "WHERE obj.date BETWEEN :minDate AND :maxDate "
            + "AND LOWER(obj.seller.name) LIKE CONCAT('%', LOWER(:name),'%') " +
            "ORDER BY obj.date DESC"
           )
     Page<SaleMinDTO> getReport(@Param("minDate") LocalDate minDate,
                                @Param("maxDate") LocalDate maxDate,
                                @Param("name") String name,
                                Pageable pageable);



    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinSummaryDTO(" +
            "SUM(obj.amount), obj.seller.name) " +
            "FROM Sale obj "
            + "WHERE obj.date BETWEEN :minDate AND :maxDate "
            + "GROUP BY obj.seller.name")
    List<SaleMinSummaryDTO> getSummary(@Param("minDate")LocalDate minDate,
                                       @Param("maxDate") LocalDate maxDate);
}

package com.alexhiz.store.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexhiz.store.config.GenericMapper;
import com.alexhiz.store.dto.SaleDTO;
import com.alexhiz.store.model.Sale;
import com.alexhiz.store.service.ISaleService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/sales")
public class SaleController {
    private final ISaleService service;
    private final GenericMapper mapper;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAll() throws Exception {
        List<SaleDTO> list = service.getAllSales();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<SaleDTO>> getAllPageable(Pageable pageable) throws Exception {
        Page<Sale> salesPage = service.listPage(pageable);
        Page<SaleDTO> page = salesPage.map(sale -> mapper.toDto(sale, SaleDTO.class, modelMapper));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable UUID id) throws Exception {
        SaleDTO dto = mapper.toDto(service.findById(id), SaleDTO.class, modelMapper);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<SaleDTO> save(@RequestBody SaleDTO dto) throws Exception {
        SaleDTO saleDto = service.createSale(dto);
        return ResponseEntity.ok(saleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@PathVariable UUID id, @RequestBody SaleDTO dto) throws Exception {
        Sale sale = service.update(id, mapper.toEntity(dto, Sale.class, modelMapper));
        SaleDTO saleDTO = mapper.toDto(sale, SaleDTO.class, modelMapper);
        return ResponseEntity.ok(saleDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        service.deleteSale(id);
    }

}

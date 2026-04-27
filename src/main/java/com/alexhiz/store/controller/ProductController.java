package com.alexhiz.store.controller;

import lombok.RequiredArgsConstructor;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alexhiz.store.config.GenericMapper;
import com.alexhiz.store.dto.ProductDTO;
import com.alexhiz.store.model.Product;
import com.alexhiz.store.service.IProductService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService service;
    private final GenericMapper mapper;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() throws Exception {
        List<ProductDTO> list = mapper.mapList(service.findAll(), ProductDTO.class, modelMapper);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Product>> getAllPageable(Pageable pageable) throws Exception {
        Page<Product> page = service.listPage(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable UUID id) throws Exception {
        ProductDTO dto = mapper.toDto(service.findById(id), ProductDTO.class, modelMapper);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO dto) throws Exception {
        Product product = service.save(mapper.toEntity(dto, Product.class, modelMapper));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable UUID id, @RequestBody ProductDTO dto) throws Exception {
        Product product = service.update(id, mapper.toEntity(dto, Product.class, modelMapper));
        ProductDTO productDTO = mapper.toDto(product, ProductDTO.class, modelMapper);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
    }
}

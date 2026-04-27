package com.alexhiz.store.service.impl;

import com.alexhiz.store.config.GenericMapper;
import com.alexhiz.store.dto.DetailSaleDTO;
import com.alexhiz.store.dto.SaleDTO;
import com.alexhiz.store.exception.ModelNotFoundException;
import com.alexhiz.store.model.Branch;
import com.alexhiz.store.model.DetailSale;
import com.alexhiz.store.model.Product;
import com.alexhiz.store.model.Sale;
import com.alexhiz.store.model.SaleStatus;
import com.alexhiz.store.repository.IBranchRepo;
import com.alexhiz.store.repository.IGenericRepo;
import com.alexhiz.store.repository.IProductRepo;
import com.alexhiz.store.repository.ISaleRepo;
import com.alexhiz.store.service.ISaleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleImpl extends CRUDImpl<Sale, UUID> implements ISaleService {
    private final ISaleRepo repo;
    private final IProductRepo productRepo;
    private final IBranchRepo branchRepo;
    private final GenericMapper mapper;
    private final ModelMapper modelMapper;

    @Override
    protected IGenericRepo<Sale, UUID> getRepo() {
        return repo;
    }

    /*
     * @Override
     * 
     * @Transactional
     * public SaleDTO createSale(SaleDTO saleDto) {
     * if (saleDto == null)
     * throw new ModelNotFoundException("SaleDTO es null");
     * if (saleDto.getDate() == null)
     * throw new ModelNotFoundException("Debe indicar la fecha");
     * if (saleDto.getDetail() == null || saleDto.getDetail().isEmpty())
     * throw new ModelNotFoundException("Debe indicar el detalle");
     * 
     * // Buscar la sucursal
     * Branch branch = branchRepo.findById(saleDto.getBranchId()).orElse(null);
     * if (branch == null) {
     * throw new ModelNotFoundException("Sucursal no encontrada");
     * }
     * 
     * Sale sale = new Sale();
     * sale.setDate(saleDto.getDate());
     * sale.setStatus(saleDto.getStatus());
     * sale.setBranch(branch);
     * sale.setTotal(saleDto.getTotal());
     * 
     * List<DetailSale> detalles = new ArrayList<>();
     * Double totalCalculado = 0.0;
     * 
     * for (DetailSaleDTO detDTO : saleDto.getDetail()) {
     * Product p = productRepo.findById(detDTO.getProductId()).orElse(null);
     * if (p == null) {
     * throw new ModelNotFoundException("Producto no encontrado: " +
     * detDTO.getProductId());
     * }
     * 
     * DetailSale detailSale = new DetailSale();
     * detailSale.setProduct(p);
     * detailSale.setUnitPrice(detDTO.getUnitPrice());
     * detailSale.setQuantity(detDTO.getQuantity());
     * detailSale.setSubtotal(detDTO.getSubtotal());
     * detailSale.setSale(sale);
     * 
     * detalles.add(detailSale);
     * totalCalculado = totalCalculado + (detDTO.getUnitPrice() *
     * detDTO.getQuantity());
     * 
     * }
     * sale.setDetail(detalles);
     * sale.setTotal(totalCalculado);
     * 
     * sale = repo.save(sale);
     * 
     * SaleDTO saleDTO = mapper.toDto(sale, SaleDTO.class, modelMapper);
     * return saleDTO;
     * }
     */

    @Override
    @Transactional
    public SaleDTO createSale(SaleDTO saleDto) {
        // 1. Convertir el DTO completo a Entidad (ModelMapper crea las instancias por
        // ti)
        Sale sale = mapper.toEntity(saleDto, Sale.class, modelMapper);

        // 2. Cargar la Sucursal directamente al objeto sale
        sale.setBranch(branchRepo.findById(saleDto.getBranchId())
                .orElseThrow(() -> new ModelNotFoundException("Sucursal no encontrada")));

        // 3. Procesar los detalles usando Streams para evitar el 'for' y las
        // instanciaciones manuales
        double totalCalculado = sale.getDetail().stream().mapToDouble(detail -> {
            // Buscamos el producto para asegurar que existe y traer su info actualizada
            Product p = productRepo.findById(detail.getProduct().getId())
                    .orElseThrow(() -> new ModelNotFoundException("Producto no encontrado"));

            detail.setProduct(p);
            detail.setSale(sale); // Vinculamos el detalle con la venta (para el sale_id)

            // Calculamos el subtotal del detalle
            double subtotal = detail.getUnitPrice() * detail.getQuantity();
            detail.setSubtotal(subtotal);

            return subtotal;
        }).sum(); // Sumamos todos los subtotales directamente

        sale.setTotal(totalCalculado);

        // 4. Guardar todo en cascada
        Sale savedSale = repo.save(sale);

        // 5. Retornar el DTO mapeado de la entidad persistida
        return mapper.toDto(savedSale, SaleDTO.class, modelMapper);
    }

    @Override
    public void deleteSale(UUID id) {
        Sale sale = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("Sale not found"));
        sale.setStatus(SaleStatus.CANCELLED);
        sale.setLogicallyDeleted(true);
        repo.save(sale);
    }

    @Override
    public List<SaleDTO> getAllSales() {
        List<Sale> ventas = repo.findAll();
        List<SaleDTO> saleDTOs = new ArrayList<>();

        SaleDTO dto;
        for (Sale v : ventas) {
            dto = mapper.toDto(v, SaleDTO.class, modelMapper);
            saleDTOs.add(dto);
        }

        return saleDTOs;
    }

    @Override
    public SaleDTO updateSale(UUID id, SaleDTO saleDto) {
        Sale sale = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("Sale not found"));
        sale.setDate(saleDto.getDate());
        sale.setStatus(saleDto.getStatus());
        sale.setBranch(branchRepo.findById(saleDto.getBranchId()).orElse(null));
        sale.setTotal(saleDto.getTotal());
        return mapper.toDto(repo.save(sale), SaleDTO.class, modelMapper);
    }
}

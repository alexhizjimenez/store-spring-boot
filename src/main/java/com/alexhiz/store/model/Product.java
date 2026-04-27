package com.alexhiz.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false, length = 100)
    private String category;
    @Column(nullable = false)
    private Double currentPrice;
}

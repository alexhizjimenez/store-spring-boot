package com.alexhiz.store.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {

    private UUID id;
    private String name;
    private String address;
    private String telephone;
}

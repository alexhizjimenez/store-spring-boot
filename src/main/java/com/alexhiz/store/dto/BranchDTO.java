package com.alexhiz.store.dto;

import jakarta.persistence.Column;

import java.util.UUID;

public class BranchDTO {

    private UUID id;
    private String name;
    private String address;
    private String telephone;
}

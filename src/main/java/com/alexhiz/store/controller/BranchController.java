package com.alexhiz.store.controller;

import com.alexhiz.store.config.GenericMapper;
import com.alexhiz.store.dto.BranchDTO;
import com.alexhiz.store.model.Branch;
import com.alexhiz.store.service.IBranchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/branches")
public class BranchController {
    private final IBranchService service;
    private final GenericMapper mapper;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAll() throws Exception {
        List<BranchDTO> list = mapper.mapList(service.findAll(), BranchDTO.class, modelMapper);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Branch>> getAllPageable(Pageable pageable) throws Exception {
        Page<Branch> page = service.listPage(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getFindById(@PathVariable UUID id) throws Exception {
        Branch branch = service.findById(id);
        BranchDTO dto = mapper.toDto(branch, BranchDTO.class, modelMapper);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BranchDTO> save(@RequestBody BranchDTO dto) throws Exception {
        Branch branch = service.save(mapper.toEntity(dto, Branch.class, modelMapper));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(branch.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> update(@PathVariable UUID id, @RequestBody BranchDTO dto) throws Exception {
        Branch branch = service.update(id, mapper.toEntity(dto, Branch.class, modelMapper));
        BranchDTO branchDTO = mapper.toDto(branch, BranchDTO.class, modelMapper);
        return ResponseEntity.ok(branchDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
    }
}

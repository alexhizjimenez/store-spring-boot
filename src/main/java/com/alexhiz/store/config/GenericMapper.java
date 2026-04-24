package com.alexhiz.store.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericMapper {

    public <D, E> E toEntity(D dto, Class<E> entityClass, ModelMapper mapper) {
        return mapper.map(dto, entityClass);
    }

    public <E, D> D toDto(E entity, Class<D> dtoClass, ModelMapper mapper) {
        return mapper.map(entity, dtoClass);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass, ModelMapper mapper) {
        return source.stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}

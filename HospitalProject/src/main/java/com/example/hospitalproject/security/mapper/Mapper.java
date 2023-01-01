package com.example.hospitalproject.security.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<Entity, Request, Response>{
    Response mapEntityToDto(Entity entity);

    Entity mapDtoToEntity(Request request);

    default List<Response> mapEntityListToDtoList(List<Entity> entities) {
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    default List<Entity> mapDtoListToEntityList(List<Request> requestList) {
        return requestList.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}

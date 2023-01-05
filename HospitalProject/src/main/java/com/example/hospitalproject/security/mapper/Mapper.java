package com.example.hospitalproject.security.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface Mapper<Entity, Request, Response>{
    Response mapEntityToDto(Entity entity);

    Entity mapDtoToEntity(Request request);
}

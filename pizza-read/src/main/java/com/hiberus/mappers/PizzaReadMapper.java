package com.hiberus.mappers;

import com.hiberus.dtos.PizzaResponseDto;
import com.hiberus.models.Pizza;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaReadMapper {
    PizzaResponseDto pizzaToResponseDto(Pizza pizza);
}

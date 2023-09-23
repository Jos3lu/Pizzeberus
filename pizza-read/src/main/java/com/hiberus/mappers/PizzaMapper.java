package com.hiberus.mappers;

import com.hiberus.dtos.PizzaDto;
import com.hiberus.models.Pizza;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {
    PizzaDto pizzaToDto(Pizza pizza);
}

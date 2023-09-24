package com.hiberus.mappers;

import com.hiberus.dtos.PizzaCreateDto;
import com.hiberus.dtos.PizzaResponseDto;
import com.hiberus.dtos.PizzaUpdateDto;
import com.hiberus.models.Pizza;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaWriteMapper {
    Pizza dtoCreateToPizza(PizzaCreateDto pizzaCreateDto);
    Pizza dtoUpdateToPizza(PizzaUpdateDto pizzaUpdateDto);
    Pizza responseDtoToPizza(PizzaResponseDto pizzaResponseDto);
    PizzaResponseDto pizzaToResponseDto(Pizza pizza);
}

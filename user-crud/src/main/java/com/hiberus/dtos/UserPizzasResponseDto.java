package com.hiberus.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPizzasResponseDto {
    private Long id;
    private String name;
    private List<PizzaResponseDto> pizzas;
}

package com.hiberus.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PizzaResponseDto {
    private Long id;
    private String name;
}

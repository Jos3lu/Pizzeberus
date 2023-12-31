package com.hiberus.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGeneralResponseDto {
    private Long id;
    private String name;
    private List<Long> pizzas;
}

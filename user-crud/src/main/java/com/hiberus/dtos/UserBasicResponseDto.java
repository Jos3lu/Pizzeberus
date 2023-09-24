package com.hiberus.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicResponseDto {
    private Long id;
    private String name;
}

package com.hiberus.mappers;

import com.hiberus.dtos.*;
import com.hiberus.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoCreateToUser(UserCreateDto userCreateDto);
    User dtoUpdateToUser(UserUpdateDto userUpdateDto);
    UserBasicResponseDto userToBasicResponseDto(User user);
    UserGeneralResponseDto userToGeneralResponseDto(User user);
    @Mapping(target = "pizzas", ignore = true)
    UserPizzasResponseDto userToUserPizzasResponseDto(User user);
}

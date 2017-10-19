package com.github.netcracker2017team.rest.controller.converter;

import com.github.netcracker2017team.model.UserDto;
import com.github.netcracker2017team.rest.model.User;

/**
 * @author Alex Ivchenko
 */
public class UserDtoConverter {
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public User fromDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }
}

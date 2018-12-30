package org.myaldoc.authentication.service.connection.models.mappings.impl;

import org.myaldoc.authentication.service.connection.models.Role;
import org.myaldoc.authentication.service.connection.models.User;
import org.myaldoc.authentication.service.connection.models.dto.UserDTO;
import org.myaldoc.authentication.service.connection.models.mappings.UserMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public UserDTO toDto(User user) {
    return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .email(user.getEmail())
            .roles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
            .build();
  }

  @Override
  public User toEntity(UserDTO dto) {
    return User.builder()
            .id(dto.getId())
            .username(dto.getUsername())
            .password(dto.getPassword())
            .email(dto.getEmail())
            .build();
  }
}

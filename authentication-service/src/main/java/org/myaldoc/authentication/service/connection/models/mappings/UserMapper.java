package org.myaldoc.authentication.service.connection.models.mappings;

import org.myaldoc.authentication.service.connection.models.User;
import org.myaldoc.authentication.service.connection.models.dto.UserDTO;


public interface UserMapper {
  UserDTO toDto(User user);

  User toEntity(UserDTO dto);
}

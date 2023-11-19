package com.codoacodo23650.tpgrupo14.mappers;

import com.codoacodo23650.tpgrupo14.entities.User;
import com.codoacodo23650.tpgrupo14.entities.dtos.UserDto;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;


/* @UtilityClass
Todos los métodos definidos en la clase se marcan automáticamente como static.
Se agrega un constructor privado para evitar la instanciación de la clase.
La clase se marca como final para evitar la herencia.*/
@UtilityClass
public class UserMapper {

    public User dtoTouser(UserDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAddress(dto.getAddress());
        user.setDni(dto.getDni());
        user.setBirthday_date(dto.getBirthday_date());
        user.setEmail(dto.getEmail());
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());
        return user;
    }

    public UserDto userToDto(User user){
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setId(user.getId());
        dto.setAddress(user.getAddress());
        dto.setEmail(user.getEmail());
        dto.setBirthday_date(user.getBirthday_date());
        dto.setDni(user.getDni());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());
        return dto;
    }
}



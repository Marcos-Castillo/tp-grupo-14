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
        user.setFirstname(dto.getFirstname());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setDni(dto.getDni());
        user.setGender(dto.getGender());
        user.setCivil_status(dto.getCivil_status());
        user.setPhonenumber(dto.getPhonenumber());
        user.setAddress(dto.getAddress());
        user.setBirthdate(dto.getBirthdate());
        user.setStatus(dto.getStatus());
        user.setCreated_at(dto.getCreated_at());
        user.setUpdated_at(dto.getUpdated_at());
        user.setDeleted_at(dto.getDeleted_at());
        user.setLast_login(dto.getLast_login());
        user.setFailed_attempts(dto.getFailed_attempts());
        user.setAccounts(dto.getAccounts());
        return user;
    }

    public UserDto userToDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setFirstname(user.getFirstname());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setDni(user.getDni());
        dto.setGender(user.getGender());
        dto.setCivil_status(user.getCivil_status());
        dto.setPhonenumber(user.getPhonenumber());
        dto.setAddress(user.getAddress());
        dto.setBirthdate(user.getBirthdate());
        dto.setStatus(user.getStatus());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());
        dto.setDeleted_at(user.getDeleted_at());
        dto.setLast_login(user.getLast_login());
        dto.setFailed_attempts(user.getFailed_attempts());
        dto.setAccounts(user.getAccounts());
        return dto;
    }
}
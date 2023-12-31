package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.User;
import com.codoacodo23650.tpgrupo14.entities.dtos.UserDto;
import com.codoacodo23650.tpgrupo14.mappers.UserMapper;
import com.codoacodo23650.tpgrupo14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public List<UserDto> getUsers(){
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id){
        User user = repository.findById(id).get();
        return UserMapper.userToDto(user);
    }

    public UserDto createUser(UserDto user){
        User entity = UserMapper.dtoTouser(user);
        entity.setCreated_at(LocalDateTime.now());
        User entitySaved = repository.save(entity);
        return UserMapper.userToDto(entitySaved);
    }

    public String deleteUser(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "El usuario con id: " + id + " ha sido eliminado";
        } else {
            return "El usuario con id: " + id + ", no ha sido eliminado";
        }
    }

    public UserDto updateUser(Long id, UserDto dto){
        if(repository.existsById(id)){
            User userToModify = repository.findById(id).get();
            //validar que datos no vienen null parea setear en el objeto

            //logica del Patch
            if (dto.getUsername() != null){
                userToModify.setUsername(dto.getUsername());
            }

            if (dto.getPassword() != null){
                userToModify.setPassword(dto.getPassword());
            }

            if (dto.getEmail() != null){
                userToModify.setEmail(dto.getEmail());
            }

            if (dto.getDni() != null){
                userToModify.setDni(dto.getDni());
            }

            if (dto.getAddress() != null){
                userToModify.setAddress(dto.getAddress());
            }

            if (dto.getBirthday_date() != null){
                userToModify.setBirthday_date(dto.getBirthday_date());
            }
            userToModify.setUpdated_at(LocalDateTime.now());
            User userModified = repository.save(userToModify);
            return UserMapper.userToDto(userModified);
        }
        return null;
    }

}
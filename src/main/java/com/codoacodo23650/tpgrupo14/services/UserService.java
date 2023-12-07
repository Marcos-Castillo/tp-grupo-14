package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.User;
import com.codoacodo23650.tpgrupo14.entities.dtos.UserDto;
import com.codoacodo23650.tpgrupo14.exceptions.exceptionKinds.UserBadRequestException;
import com.codoacodo23650.tpgrupo14.exceptions.exceptionKinds.UserNotFoundException;
import com.codoacodo23650.tpgrupo14.mappers.UserMapper;
import com.codoacodo23650.tpgrupo14.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        List<UserDto> usersDtos = users.stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
        return usersDtos;
    }

    public UserDto getUserById(Long id){
        //User user = repository.findById(id).get();
        User user = repository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("No se encontró un usuario con ese id"));
        user.setPassword("******");
        return UserMapper.userToDto(user);
    }

    public UserDto createUser(UserDto user){
        Boolean emailExist = repository.existsByEmail(user.getEmail());
        if(emailExist) throw new UserBadRequestException("Ya existe un usuario con ese email");

        User entity = UserMapper.dtoTouser(user);
        entity.setCreated_at(LocalDateTime.now());
        User entitySaved = repository.save(entity);
        user = UserMapper.userToDto(entitySaved);
        user.setPassword("******");
        return user;
    }

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

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
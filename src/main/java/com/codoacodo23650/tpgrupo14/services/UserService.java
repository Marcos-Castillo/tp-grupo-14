package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.User;
import com.codoacodo23650.tpgrupo14.entities.dtos.UserDto;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusUser;
import com.codoacodo23650.tpgrupo14.mappers.UserMapper;
import com.codoacodo23650.tpgrupo14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public List<User> getUsers(){
        List<User> users = repository.findAll();
        return users;
    }

    public User getUserById(Long id){
        try {
            User user = repository.findById(id).get();
            System.out.println(user);
            if(user != null){
                user.setPassword("******");
            }
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public UserDto createUser(UserDto user){
        User entity = UserMapper.dtoTouser(user);
        entity.setCreated_at(LocalDateTime.now());
        entity.setStatus(StatusUser.ENABLED);
        entity.setFailed_attempts(0);
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
            return "El usuario con ID " + id + " ha sido eliminado.";
        } else {
            return null;
        }
    }

    public UserDto updateUser(Long id, UserDto dto){
        if(repository.existsById(id)){
            User userToModify = repository.findById(id).get();
            // Validar que datos no vienen null parea setear en el objeto
            // logica del Patch
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

            if (dto.getBirthdate() != null){
                userToModify.setBirthdate(dto.getBirthdate());
            }
            userToModify.setUpdated_at(LocalDateTime.now());
            User userModified = repository.save(userToModify);
            return UserMapper.userToDto(userModified);
        }
        return null;
    }

    //
    public User logicallyDeleteUser(Long id){
        if(repository.existsById(id)){
            User userToModify = repository.findById(id).get();
            userToModify.setDeleted_at(LocalDateTime.now());
            userToModify.setStatus(StatusUser.REMOVED);
            User userModified = repository.save(userToModify);
            if(userModified != null){
                return userModified;
            }
        }
        return null;
    }

    public Object loginUser(String usuario, String contrasenia){
        User entity = repository.findByUsername(usuario);
        if (entity != null && entity.getPassword().equals(contrasenia)){
            switch (entity.getStatus()){
                case LOCKED -> {
                    //return ResponseEntity.status(HttpStatus.OK).body("Usuario bloqueado.");
                    return "Usuario bloqueado.";
                }
                case DISABLED -> {
                    return "Usuario desactivado.";
                }
                case ENABLED -> {
                    entity.setFailed_attempts(0);
                    entity.setLast_login(LocalDateTime.now());
                    repository.save(entity);
                    return entity;
                }
                case REMOVED -> {
                    return "Usuario no disponible.";
                }
                case SUSPENDED -> {
                    return "Usuario suspendido.";
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } else if (entity != null && !entity.getPassword().equals(contrasenia)) {
            if(entity.getFailed_attempts() < 2 ){
                entity.setFailed_attempts(entity.getFailed_attempts() + 1);
                repository.save(entity);
                return "Verificar datos.";
            }else{
                entity.setStatus(StatusUser.LOCKED);
                repository.save(entity);
                return "Usuario bloqueado.";
            }
        }
        // El usuario no existe.
        return null;
    }

    public Object unlockUser(String username){
        System.out.println(repository.existsByUsername(username));
        System.out.println(username);
        if(repository.existsByUsername(username)){
            User userToModify = repository.findByUsername(username);
            System.out.println(userToModify);
            if(userToModify.getStatus() == StatusUser.LOCKED){
                userToModify.setStatus(StatusUser.ENABLED);
                userToModify.setFailed_attempts(0);
                User userModified = repository.save(userToModify);
                return userModified;
            } else if (userToModify.getStatus() == StatusUser.ENABLED) {
                return 0;
            }
            return "El usuario no se encuentra bloqueado ni habilitado.";
        }
        return null;
    }

}
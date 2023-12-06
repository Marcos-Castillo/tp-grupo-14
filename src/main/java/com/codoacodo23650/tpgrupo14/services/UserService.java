package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.User;
import com.codoacodo23650.tpgrupo14.entities.dtos.AccountDto;
import com.codoacodo23650.tpgrupo14.entities.dtos.UserDto;
import com.codoacodo23650.tpgrupo14.entities.enums.AccountType;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusUser;
import com.codoacodo23650.tpgrupo14.exceptions.TransferNotFoundException;
import com.codoacodo23650.tpgrupo14.mappers.AccountMapper;
import com.codoacodo23650.tpgrupo14.mappers.UserMapper;
import com.codoacodo23650.tpgrupo14.repositories.AccountRepository;
import com.codoacodo23650.tpgrupo14.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository repository;
    private final AccountRepository repo_acc;

    public UserService(UserRepository repository, AccountRepository repo_acc){

        this.repository = repository;
        this.repo_acc = repo_acc;
    }

    public List<UserDto> getUsers(){
        List<User> users = repository.findAll();
        List<UserDto> usersDtos = users.stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
        return usersDtos;
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
        if(!(repository.findByUsername(user.getUsername()) == null))
        {
            throw new TransferNotFoundException("Username " + user.getUsername() + " already exist." );
        }

        User entity = UserMapper.dtoTouser(user);
        entity.setCreated_at(LocalDateTime.now());
        entity.setStatus(StatusUser.ENABLED);
        entity.setFailed_attempts(0);
        User entitySaved = repository.save(entity);

        if(!(entitySaved == null))
        {
            createAutomaticAccount(entitySaved);
        }

        user = UserMapper.userToDto(entitySaved);
        user.setPassword("******");
        return user;
    }


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
                if(!(repository.findByUsername(dto.getUsername()) == null))
                {
                    throw new TransferNotFoundException("Username " + dto.getUsername() + " already exist." );
                }
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

    public AccountDto createAutomaticAccount(User user){
        String cbu;
        cbu = getNewCbu();

        Account entity = new Account();
        AccountDto accountDto;

        entity.setType(AccountType.DOLLAR_SAVINGS_BANK);
        entity.setCbu(cbu);
        entity.setAlias(user.getFirstname().split(" ")[0].toLowerCase() + "." + user.getSurname().split(" ")[0].toLowerCase() + "." + user.getUsername().split(" ")[0].toLowerCase());
        entity.setAmount(0.0);
        entity.setOwner(user);

        Account entitySaved = repo_acc.save(entity);
        accountDto = AccountMapper.accountToDto(entitySaved);
        return accountDto;
    }

    public  String  getNewCbu()
    {
        String newCbu;
        String bank = String.format("%03d", (int)(Math.random()*1000));
        String branch = String.format("%04d", (int)(Math.random()*10000));
        String accNum = String.format("%04d", (int)((Math.random()*10000)+ 1)) + String.format("%09d", (int)(Math.random()*1000000000));

        Long firVerif = Long.parseLong(String.valueOf(bank.charAt(0))) * 7 +
                Long.parseLong(String.valueOf(bank.charAt(1))) * 1 +
                Long.parseLong(String.valueOf(bank.charAt(2))) * 3 +
                Long.parseLong(String.valueOf(branch.charAt(0))) * 9 +
                Long.parseLong(String.valueOf(branch.charAt(1))) * 7 +
                Long.parseLong(String.valueOf(branch.charAt(2))) * 1 +
                Long.parseLong(String.valueOf(branch.charAt(3))) * 3;

        String strVerif = Long.toString(firVerif);
        firVerif = 10 - Long.parseLong(String.valueOf(strVerif.charAt(strVerif.length() - 1)));

        Long secVerif =
                Long.parseLong(String.valueOf(accNum.charAt(0))) * 3 +
                        Long.parseLong(String.valueOf(accNum.charAt(1))) * 9 +
                        Long.parseLong(String.valueOf(accNum.charAt(2))) * 7 +
                        Long.parseLong(String.valueOf(accNum.charAt(3))) * 1 +
                        Long.parseLong(String.valueOf(accNum.charAt(4))) * 3 +
                        Long.parseLong(String.valueOf(accNum.charAt(5))) * 9 +
                        Long.parseLong(String.valueOf(accNum.charAt(6))) * 7 +
                        Long.parseLong(String.valueOf(accNum.charAt(7))) * 1 +
                        Long.parseLong(String.valueOf(accNum.charAt(8))) * 3 +
                        Long.parseLong(String.valueOf(accNum.charAt(9))) * 9 +
                        Long.parseLong(String.valueOf(accNum.charAt(10))) * 7 +
                        Long.parseLong(String.valueOf(accNum.charAt(11))) * 1 +
                        Long.parseLong(String.valueOf(accNum.charAt(12))) * 3;

        strVerif = Long.toString(secVerif);
        secVerif = 10 - Long.parseLong(String.valueOf(strVerif.charAt(strVerif.length() - 1)));

        newCbu = bank + branch + firVerif + accNum + secVerif;

        return(newCbu);
    }
}
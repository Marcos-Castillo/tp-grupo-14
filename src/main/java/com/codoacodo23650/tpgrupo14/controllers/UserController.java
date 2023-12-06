package com.codoacodo23650.tpgrupo14.controllers;

import com.codoacodo23650.tpgrupo14.entities.User;
import com.codoacodo23650.tpgrupo14.entities.dtos.UserDto;
import com.codoacodo23650.tpgrupo14.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    // Obtener una lista de usuarios registrados

    //@GetMapping(value = "/")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getUsers());
    }

    // Obtener un usuario por ID.
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        User usuario = service.getUserById(id);
        if(usuario != null)
        {
            return new ResponseEntity<Map>(responseRequest(usuario,null,null), HttpStatus.OK);
        }
        return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_FOUND","Usuario no encontrado."), HttpStatus.NOT_FOUND);
    }

    // Crear un usuario.
    //@PostMapping(value = "/create")
    @PostMapping
    public ResponseEntity<Map> createUser(@RequestBody UserDto user){
        UserDto entity = service.createUser(user);
        if(entity != null){
            return new ResponseEntity<Map>(responseRequest(entity,null,"Usuario creado."), HttpStatus.CREATED);
        }
        return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_CREATED","Verificar datos ingresados."), HttpStatus.NOT_IMPLEMENTED);
    }

    // Modificar totalmente un usuario por ID.
    //@PutMapping(value = "/edit/{id}")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Map> updateFullUser(@PathVariable Long id, @RequestBody UserDto userDto)
    {
        UserDto entity = service.updateUser(id, userDto);
        if(entity != null){
            return new ResponseEntity<Map>(responseRequest(entity,null,"Usuario actualizado."), HttpStatus.OK);
        }
        return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_UPDATED","Verificar datos ingresados."), HttpStatus.NOT_IMPLEMENTED);
    }

    // Modificar parcialmente un usuario por ID.
    @PatchMapping(value = "/edit/{id}")
    public ResponseEntity<Map> updatePartialUser(@PathVariable Long id, @RequestBody UserDto userDto)
    {
        UserDto entity = service.updateUser(id, userDto);
        if(entity != null){
            return new ResponseEntity<Map>(responseRequest(entity,null,"Usuario actualizado."), HttpStatus.OK);
        }
        return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_UPDATED","Verificar datos ingresados."), HttpStatus.NOT_IMPLEMENTED);
    }

    // Eliminar un usuario por ID.
    //@DeleteMapping(value = "/delete/{id}")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map> deleteUser(@PathVariable Long id){
        String response = service.deleteUser(id);
        if(response != null){
            return new ResponseEntity<Map>(responseRequest(null,null,response), HttpStatus.OK);
        }
        return new ResponseEntity<Map>(responseRequest(null,null,"Verificar datos proporcionados."), HttpStatus.NOT_IMPLEMENTED);
    }

    // Eliminar logicamente un usuario por ID.
    @PatchMapping(value = "/delete/{id}")
    public ResponseEntity<Map> logicallyDeleteUser(@PathVariable Long id){
        User entity = service.logicallyDeleteUser(id);
        if(entity != null){
            return new ResponseEntity<Map>(responseRequest(entity,null,"El usuario "+entity.getClient()+" fue dado de baja."), HttpStatus.OK);
        }
        return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_DELETED","Verificar datos ingresados."), HttpStatus.NOT_IMPLEMENTED);
    }

    // Iniciar sesión con un usuario.
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials){
        if(credentials.get("username") != null && credentials.get("contrasenia") != null)
        {
            Object response = service.loginUser(credentials.get("username"), credentials.get("contrasenia"));
            if(response instanceof String){
                String message = response.toString();
                return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_DATA",message), HttpStatus.OK);
            } else if (response instanceof User) {
                return new ResponseEntity<Map>(responseRequest(response,null,null), HttpStatus.OK);
            }
            return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_DATA","El usuario no existe."), HttpStatus.OK);

        }
        return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_DATA","Datos no proporcionados."), HttpStatus.OK);
    }

    // Desbloquear un usuario.
    @PostMapping(value = "/unlock")
    public ResponseEntity<Map> unlockUser(@RequestBody String username){

        Object response = service.unlockUser(username);
        if(response instanceof User){
            return new ResponseEntity<Map>(responseRequest(response,null,"Usuario desbloqueado"), HttpStatus.OK);
        } else if (response instanceof String) {
            String message = response.toString();
            return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_UPDATED",message), HttpStatus.OK);
        } else if (response instanceof Integer) {
            return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_UPDATED","El usuario se encontraba habilitado."), HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<Map>(responseRequest(null,"ERROR_NOT_UPDATED","Verificar datos proporcionados."), HttpStatus.NOT_FOUND);
    }

    // Mapear la respuesta.
    private Map<String, Object> responseRequest(Object entity, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", entity);
        response.put("error", error);
        response.put("message", message);
        return response;
    }
}
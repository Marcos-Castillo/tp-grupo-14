package com.codoacodo23650.tpgrupo14.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario")
    private String username;

    @Column(name = "contrasenia")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "dni")
    private String dni;

    @Column(name = "direccion")
    private String address;

    @Column(name = "fecha_cumpleanios")
    private Date birthday_date;

    @Column(name = "fecha_creacion")
    private LocalDateTime created_at;

    @Column(name = "fecha_modificacion")
    private LocalDateTime updated_at;

    @JsonManagedReference
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Account> accounts;

    /*
    @JsonManagedReference y @JsonBackReference:

    Uso: Se utilizan en una relación bidireccional para manejar cómo se serializan las entidades relacionadas.
    Efecto:
    @JsonManagedReference: Se coloca en el lado "administrador" de la relación (el lado que inicia la relación).
    * Indica que Jackson debe incluir la propiedad marcada durante la serialización.
    @JsonBackReference: Se coloca en el lado "respaldo" de la relación (el lado que no inicia la relación).
    * Indica que Jackson debe ignorar la propiedad marcada durante la serialización para evitar ciclos infinitos.
   */
}

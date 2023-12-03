package com.codoacodo23650.tpgrupo14.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.codoacodo23650.tpgrupo14.entities.enums.CivilStatusUser;
import com.codoacodo23650.tpgrupo14.entities.enums.GenderUser;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusUser;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(name = "usuario")
    private String username;

    @Column(name = "contrasenia")
    private String password;

    @Column(name = "nombre")
    private String firstname;

    @Column(name = "apellido")
    private String surname;

    @Column(name = "correo_electronico")
    private String email;

    @Column(name = "dni")
    private String dni;

    @Column(name = "genero")
    private GenderUser gender;

    @Column(name = "estado_civil")
    private CivilStatusUser civil_status;

    @Column(name = "telefono")
    private String phonenumber;

    @Column(name = "direccion")
    private String address;

    @Column(name = "fecha_nacimiento")
    private Date birthdate;

    @Column(name = "estado")
    private StatusUser status;

    @Column(name = "fecha_creacion")
    private LocalDateTime created_at;

    @Column(name = "fecha_modificacion")
    private LocalDateTime updated_at;

    @Column(name = "fecha_baja")
    private LocalDateTime deleted_at;

    @Column(name = "ultimo_acceso")
    private LocalDateTime last_login;

    @Column(name = "intentos_fallidos")
    private Integer failed_attempts;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Account> accounts;

    public String getClient(){
        return this.firstname + " " + this.surname;
    }
}

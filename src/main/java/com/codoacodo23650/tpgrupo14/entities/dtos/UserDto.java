package com.codoacodo23650.tpgrupo14.entities.dtos;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.enums.CivilStatusUser;
import com.codoacodo23650.tpgrupo14.entities.enums.GenderUser;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String firstname;

    private String surname;

    private String email;

    private String dni;

    private GenderUser gender;

    private CivilStatusUser civil_status;

    private String phonenumber;

    private String address;

    private Date birthdate;

    private StatusUser status;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private LocalDateTime deleted_at;

    private LocalDateTime last_login;

    private Integer failed_attempts;

    //private List<Account> accounts;
}
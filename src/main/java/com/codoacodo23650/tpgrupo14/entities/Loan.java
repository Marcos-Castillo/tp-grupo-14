package com.codoacodo23650.tpgrupo14.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Table(name = "préstamos")
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "importe")
    private Double amount;

    @Column(name = "interes")
    private Double interest;

    @Column(name = "plazo_cuotas")
    private Long dues;

    @Column(name = "fecha_creacion")
    private LocalDateTime created_at;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime updated_at;

    //@Column(name = "estado")
    //private StatusLoan status;

    //@ManyToOne
    //@JoinColumn(name = "account_id")
    //private Account account;
    //@OneToMany(mappedBy = "loan")
    //private List<ClientLoan> client_load;
    private Long client_load;

}

package com.codoacodo23650.tpgrupo14.entities;

import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "préstamos_clientes")
@Getter
@Setter
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_cuotas")
    private Long dues;

    @Column(name = "cuotas_pendientes")
    private Long pendDues;

    @Column(name = "estado")
    private StatusLoan status;

    @Column(name = "fecha_creacion")
    private LocalDateTime created_at;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_loan")
    private Loan loan;
}

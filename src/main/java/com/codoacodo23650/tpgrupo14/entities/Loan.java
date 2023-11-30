package com.codoacodo23650.tpgrupo14.entities;

import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column(name = "cuotas")
    private Long dues;

    @Column(name = "fecha_prestamo")
    private LocalDateTime date;

    @Column(name = "estado")
    private StatusLoan status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}

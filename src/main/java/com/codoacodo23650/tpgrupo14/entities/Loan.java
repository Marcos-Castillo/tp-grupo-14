package com.codoacodo23650.tpgrupo14.entities;

import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private Account account;

    @Column
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Installments> installments;

}

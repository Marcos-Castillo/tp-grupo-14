package com.codoacodo23650.tpgrupo14.entities;

import com.codoacodo23650.tpgrupo14.entities.Loan;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "prestamos_cuotas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Installments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Loan loan;

    @Column(name = "nro_cuota")
    private Long IntallmentNumber;

    @Column(name = "importe")
    private BigDecimal ammount;

    @Column(name = "fecha_vencimiento")
    private Date dueDate;

    @Column(name = "fecha_pago")
    private Date PaymentDate;

}

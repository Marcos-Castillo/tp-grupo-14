package com.codoacodo23650.tpgrupo14.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "CUENTAS")
@Getter
@Setter
@Builder//patron de diseño builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "cbu")
    private String cbu;

    @Column(name = "alias")
    private String alias;

    @Column(name = "monto")
    private Double amount;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}






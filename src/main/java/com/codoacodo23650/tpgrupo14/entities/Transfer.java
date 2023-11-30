    package com.codoacodo23650.tpgrupo14.entities;

    import jakarta.persistence.*;
    import lombok.*;
    import java.time.LocalDateTime;

    @Entity
    @Table(name = "transferencias")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Transfer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "monto")
        private Double amount;

        @Column(name = "cuenta_origen_id")
        private Long accountOrigin;

        @Column(name = "cuenta_destino_id")
        private Long accountDestination;

        @Column(name = "fecha_transferencia")
        private LocalDateTime date;

    }

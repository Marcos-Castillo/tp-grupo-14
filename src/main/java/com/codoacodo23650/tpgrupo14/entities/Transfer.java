    package com.codoacodo23650.tpgrupo14.entities;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
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
        @Column(name = "id")
        private Long id;

        @Column(name = "monto")
        private Double amount;

        @ManyToOne
        @JoinColumn(name = "cuenta_origen_id")
        private Account accountOrigin;

        @ManyToOne
        @JoinColumn(name = "cuenta_destino_id")
        private Account accountDestination;

        @Column(name = "fecha_transferencia")
        private LocalDateTime date;
    }

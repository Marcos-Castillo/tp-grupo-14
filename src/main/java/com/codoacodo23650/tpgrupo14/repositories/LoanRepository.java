package com.codoacodo23650.tpgrupo14.repositories;

import com.codoacodo23650.tpgrupo14.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query(value = "SELECT "
            + "p.id AS id, "
            + "p.importe AS importe, "
            + "p.interes AS interes, "
            + "p.cuotas AS cuotas, "
            + "p.valor_cuotas, "
            + "p.fecha_prestamo AS fecha_prestamo, "
            + "p.estado AS estado, "
            + "c.cuenta_id AS account_id, "
            + "p.created_at, "
            + "p.updated_at "
            + "FROM coc_23650.prestamos p "
            + "INNER JOIN coc_23650.cuentas c ON c.cuenta_id = p.account_id "
            + "WHERE c.user_id = :userId", nativeQuery = true)
    List<Loan> findLoansByUserId(@Param("userId") Long userId);

}

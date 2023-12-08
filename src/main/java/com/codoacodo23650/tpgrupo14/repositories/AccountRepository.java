package com.codoacodo23650.tpgrupo14.repositories;

import com.codoacodo23650.tpgrupo14.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    boolean existsByAlias(String alias);
}

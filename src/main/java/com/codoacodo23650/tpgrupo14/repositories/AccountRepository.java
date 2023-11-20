package com.codoacodo23650.tpgrupo14.repositories;

import com.codoacodo23650.tpgrupo14.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<User,Long> {
}

package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

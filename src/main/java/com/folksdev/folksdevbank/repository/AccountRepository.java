package com.folksdev.folksdevbank.repository;

import com.folksdev.folksdevbank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}

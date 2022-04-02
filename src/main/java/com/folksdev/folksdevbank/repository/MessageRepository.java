package com.folksdev.folksdevbank.repository;

import com.folksdev.folksdevbank.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

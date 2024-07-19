package com.demo.repository;

import com.demo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesByChatIdOrderByCreatedTimeAsc(String chatId);

    Optional<Message> findMessagesByChatIdAndContent(String chatId, String content);
}


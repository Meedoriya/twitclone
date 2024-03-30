package com.twitclone.twitclone.repository;

import com.twitclone.twitclone.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByTag(String tag);

}

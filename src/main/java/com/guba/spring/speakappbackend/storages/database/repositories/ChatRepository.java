package com.guba.spring.speakappbackend.storages.database.repositories;

import com.guba.spring.speakappbackend.storages.database.models.Chat;
import com.guba.spring.speakappbackend.storages.database.models.Patient;
import com.guba.spring.speakappbackend.storages.database.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findChatMessageByPatientAndProfessional(Patient patient, Professional professional);

    List<Chat> findChatMessageByProfessional(Professional professional);
}
package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.security.services.CustomUserDetailService;
import com.guba.spring.speakappbackend.storages.database.models.*;
import com.guba.spring.speakappbackend.storages.database.repositories.ChatMessageRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.ChatRepository;
import com.guba.spring.speakappbackend.web.schemas.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.guba.spring.speakappbackend.web.schemas.ChatMessageDTO.AuthorDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final CustomUserDetailService customUserDetailService;

    public List<ChatMessageDTO> create(ChatMessageDTO message, int limit) {
        boolean isUserPatient = customUserDetailService.getUserCurrent().isUserPatient();
        Professional professional = getProfessional();
        Patient patient = getPatient(message.getToUser());

        Chat chat = this.chatRepository
                .findChatMessageByPatientAndProfessional(patient, professional)
                .orElseGet( () -> new Chat(patient, professional));

        ChatMessage messageNew = new ChatMessage(message.getStatus(), message.getType(), message.getText(), LocalDateTime.now(), isUserPatient, chat);
        this.chatRepository.save(chat);
        this.chatMessageRepository.save(messageNew);
        return chat
                .getChatMessages()
                .stream()
                .map(this::createMessageDTO)
                .sorted(Comparator.comparing(ChatMessageDTO::getCreatedAt).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<ChatMessageDTO> getMessages(Long idToUser, int limit) {
        Professional professional = getProfessional();
        Patient patient = getPatient(idToUser);

        //set status message
        setMessageStatus(professional, patient);

        //the first is the most recent
        return this.chatRepository
                .findChatMessageByPatientAndProfessional(patient, professional)
                .stream()
                .flatMap(chat -> chat.getChatMessages().stream())
                .map(this::createMessageDTO)
                .sorted(Comparator.comparing(ChatMessageDTO::getCreatedAt).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private void setMessageStatus(Professional professional, Patient patient) {
        UserAbstract userCurrent = customUserDetailService.getUserCurrent();
        var messagesOrder = this.chatRepository
                .findChatMessageByPatientAndProfessional(patient, professional)
                .stream()
                .flatMap(chat -> chat.getChatMessages().stream())
                .sorted(Comparator.comparing(ChatMessage::getCreatedAt).reversed())
                .collect(Collectors.toList());

        if (messagesOrder.isEmpty())
            return;

        var mostRecentMessage = messagesOrder.get(0);
        if (mostRecentMessage.isPatient() ^ userCurrent.isUserPatient()) {
            var messageSeen = messagesOrder
                    .stream()
                    .filter(m -> userCurrent.isUserPatient() ^ m.isPatient())
                    .peek(m-> m.setStatus("seen"))
                    .collect(Collectors.toList());
            this.chatMessageRepository.saveAll(messageSeen);
        }

    }

    private ChatMessageDTO createMessageDTO(ChatMessage message) {
        Long idAuthor = message.getChat().getProfessional().getIdProfessional();
        String firstName = message.getChat().getProfessional().getFirstName();
        String lastName = message.getChat().getProfessional().getLastName();

        if (message.isPatient()) {
            idAuthor = message.getChat().getPatient().getIdPatient();
            firstName = message.getChat().getPatient().getFirstName();
            lastName = message.getChat().getPatient().getLastName();
        }

        return new ChatMessageDTO(
                message.getStatus(), message.getMessage(), message.getType(),
                message.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli(), message.getId(),
                new AuthorDTO(idAuthor, firstName, lastName)
                );
    }

    private Patient getPatient(Long toUser) {
        UserAbstract userCurrent = customUserDetailService.getUserCurrent();
        Patient patient;
        if (userCurrent.isUserPatient())
            patient = customUserDetailService.getUserCurrent(Patient.class);
        else
            patient = customUserDetailService.getUserCurrent(Professional.class)
                    .getPatients()
                    .stream()
                    .filter(p -> p.getIdPatient().equals(toUser))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundElementException("the professional user does not have a patient " + toUser));

        return patient;
    }

    private Professional getProfessional() {
        UserAbstract userCurrent = customUserDetailService.getUserCurrent();
        Professional professional;
        if (userCurrent.isUserPatient())
            professional = Optional
                    .of(customUserDetailService.getUserCurrent(Patient.class))
                    .map(Patient::getProfessional)
                    .orElseThrow(() -> new NotFoundElementException("the patient user does not have a professional associated"));
        else
            professional = customUserDetailService.getUserCurrent(Professional.class);
        return professional;
    }
}

package com.guba.spring.speakappbackend.storages.database.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tm_chat_message")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Long id;

    @Column(name = "status", nullable=false)
    private String status;

    @Column(name = "message", nullable=false)
    private String message;

    @Column(name = "type", nullable=false)
    private String type;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name="is_author_patient", nullable=false)
    private Boolean isPatient;

    @ManyToOne
    @JoinColumn(name = "id_chat", nullable=false)
    private Chat chat;

    public ChatMessage(String status, String type, String message, LocalDateTime createdAt, Boolean isPatient, Chat chat) {
        this.status = status;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
        this.isPatient = isPatient;
        this.chat = chat;
    }

    public boolean isPatient() {
        return Boolean.TRUE.equals(isPatient);
    }
}

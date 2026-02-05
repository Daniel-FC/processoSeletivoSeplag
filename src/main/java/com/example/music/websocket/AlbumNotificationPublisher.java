package com.example.music.websocket;

import com.example.music.model.Album;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlbumNotificationPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public AlbumNotificationPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyNewAlbum(Album album) {
        messagingTemplate.convertAndSend("/topic/albums", "New album added: " + album.getTitle());
    }
}

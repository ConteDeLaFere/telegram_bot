package pro.sky.telegrambot.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long chatId;
    private String text;
    private LocalDateTime date;

    public Notification(Long chatId, String text, LocalDateTime date) {
        this.chatId = chatId;
        this.text = text;
        this.date = date;
    }
}

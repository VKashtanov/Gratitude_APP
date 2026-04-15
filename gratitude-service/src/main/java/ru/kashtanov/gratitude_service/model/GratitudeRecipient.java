package ru.kashtanov.gratitude_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "gratitude_recipient",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gratitude_id","recipient_id"})
})
public class GratitudeRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gratitude_recipient_factory")
    @SequenceGenerator(name = "gratitude_recipient_factory", sequenceName = "gratitude_recipient_id_factory")
    @Column(name = "id")
    private Long id;

    @Column(name = "gratitude_id")
    private Long gratitudeId;

    @Column(name = "recipient_id")
    private Long recipientId;


}

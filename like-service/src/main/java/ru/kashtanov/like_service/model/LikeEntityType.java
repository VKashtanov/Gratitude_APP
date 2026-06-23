package ru.kashtanov.like_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "like_entity_type")
public class LikeEntityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "like_entity_factory")
    @SequenceGenerator(name = "like_entity_factory", sequenceName = "like_entity_id_factory", allocationSize = 1)
    @Column(name = "entity_id")
    private Long id;

    @Column(name = "entity_type")
    private String entity_type;

    @OneToMany(mappedBy = "likeEntityType")
    private List<Like> likes;

}

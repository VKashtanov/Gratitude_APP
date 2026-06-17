package ru.kashtanov.product_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Viktor Кashtanov
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_factory")
    @SequenceGenerator(name = "product_factory", sequenceName = "product_id_factory", allocationSize = 1)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;


}

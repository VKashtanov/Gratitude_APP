package ru.kashtanov.news_service.model;

import jakarta.persistence.*;
import lombok.*;
import ru.kashtanov.news_service.enums.AccaountTypeEnum;

import java.math.BigDecimal;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@Table(name = "user_accounts")
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_account_factory")
    @SequenceGenerator(name = "user_account_factory", sequenceName = "user_account_id_factory")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private AccaountTypeEnum type;

    @Column( scale = 2)
    private BigDecimal balance;

    @Column(name = "additional", unique = true)
    private String additional;

    @OneToOne(mappedBy = "userAccount")
    private News news;

    @Version
    private Long version;

}

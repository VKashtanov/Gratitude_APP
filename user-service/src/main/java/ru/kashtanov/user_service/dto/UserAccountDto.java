package ru.kashtanov.news_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kashtanov.news_service.enums.AccaountTypeEnum;

import java.math.BigDecimal;

/**
 * @author Viktor Кashtanov
 */
@Data
@AllArgsConstructor
public class UserAccountDto {
    private final Long id;
    private final AccaountTypeEnum type;
    private final String additional;
    private final BigDecimal balance;


    public UserAccountDto(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.additional = builder.additional;
        this.balance = builder.balance;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private AccaountTypeEnum type;
        private String additional;
        private BigDecimal balance;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder type(AccaountTypeEnum type) {
            this.type = type;
            return this;
        }

        public Builder additional(String additional) {
            this.additional = additional;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public UserAccountDto build() {
            return new UserAccountDto(this);
        }
    }
}

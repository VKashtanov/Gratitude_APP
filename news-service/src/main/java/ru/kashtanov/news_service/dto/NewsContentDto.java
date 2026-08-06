package ru.kashtanov.news_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kashtanov.news_service.enums.NewsContentEnum;
import ru.kashtanov.news_service.model.News;

import java.math.BigDecimal;

/**
 * @author Viktor Кashtanov
 */
@Data
@AllArgsConstructor
public class NewsContentDto {
    private final Long id;
    private final NewsContentEnum type;
    private final String additional;
    private final BigDecimal balance;


    public NewsContentDto(Builder builder) {
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
        private NewsContentEnum type;
        private String additional;
        private BigDecimal balance;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder type(NewsContentEnum type) {
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

        public NewsContentDto build() {
            return new NewsContentDto(this);
        }
    }
}

package ru.kashtanov.subscription_service.enums;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Viktor Кashtanov
 */
@Getter
public enum SubscriptionEnumType {
    DEFAULT,
    ONE_MONTH,
    SIX_MONTHS,
    ONE_YEAR,
    PREMIUM;


}

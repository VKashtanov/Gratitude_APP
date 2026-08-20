package ru.kashtanov.subscription_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.subscription_service.dto.SubscriptionDto;
import ru.kashtanov.subscription_service.exceptions.SubscriptionCrudException;
import ru.kashtanov.subscription_service.model.Subscription;

/**
 * @author Viktor Кashtanov
 */
@Component
public class ValidationService {

    public boolean validateCommon(SubscriptionDto subscription) {
        if (subscription == null) throw new SubscriptionCrudException("Subscription is null");
        if (subscription.getUserId() == null) throw new SubscriptionCrudException("UserId is null");
        if (subscription.getTargetId() == null) throw new SubscriptionCrudException("TargetId is null");
        if (subscription.getType() == null) throw new SubscriptionCrudException("Type is null");
        return true;
    }
}

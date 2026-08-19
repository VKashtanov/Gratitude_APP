package ru.kashtanov.subscription_service.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.Flow;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface SubscriptionRepo extends CrudRepository<Flow.Subscription, Integer> {
}

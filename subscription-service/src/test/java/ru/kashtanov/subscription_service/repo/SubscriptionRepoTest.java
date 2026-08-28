package ru.kashtanov.subscription_service.repo;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumType;
import ru.kashtanov.subscription_service.model.Subscription;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * @author Viktor Кashtanov
 */
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SubscriptionRepoTest {

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    //Variables
    private Subscription subscription0;
    private Subscription subscription1;
    private Subscription subscription2;
    private final Long userId_0 = 1L;
    private final Long userId_1 = 1L;
    private final Long userId_2 = 2L;
    private final Long targetId_0 = 100L;
    private final Long targetId_1 = 1L;


    @BeforeEach
    void setUp() {
        subscription0 = new Subscription();
        subscription0.setUserId(userId_0);
        subscription0.setTargetId(targetId_0);
        subscription0.setType(SubscriptionEnumType.DEFAULT);

        subscription1 = new Subscription();
        subscription1.setTargetId(targetId_1);
        subscription1.setUserId(userId_1);
        subscription1.setType(SubscriptionEnumType.DEFAULT);

        subscription2 = new Subscription();
        subscription2.setTargetId(targetId_1);
        subscription2.setUserId(userId_2);
        subscription2.setType(SubscriptionEnumType.DEFAULT);

    }

    @Test
    void findByUserId_PositiveScenario() {

        testEntityManager.persist(subscription0);
        testEntityManager.flush();
        testEntityManager.clear(); // clear persistence context , no cached intermediate data, we gotta go to db
        List<Subscription> result = subscriptionRepo.findByUserId(userId_0, null, 10L);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(userId_0);
        assertThat(result.get(0).getTargetId()).isEqualTo(targetId_0);
        assertThat(result.get(0).getType()).isEqualTo(SubscriptionEnumType.DEFAULT);
    }

    @Test
    void findByTargetId_PositiveScenario() {
        testEntityManager.persist(subscription1);
        testEntityManager.persist(subscription2);
        testEntityManager.flush();
        testEntityManager.clear(); // clear persistence context , no cached intermediate data, we have to go to db

        List<Subscription> list = subscriptionRepo.findByTargetId(targetId_1, null, 10L);
        assertThat(list.size()).isEqualTo(2);
        Subscription subs1 = list.get(0);
        Subscription subs2 = list.get(1);
        assertThat(subs1.getTargetId()).isEqualTo(targetId_1);
        assertThat(subs1.getUserId()).isEqualTo(userId_1);
        AssertionsForClassTypes.assertThat(subs1.getType()).isEqualTo(SubscriptionEnumType.DEFAULT);

        assertThat(subs2.getTargetId()).isEqualTo(targetId_1);
        assertThat(subs2.getUserId()).isEqualTo(userId_2);
        AssertionsForClassTypes.assertThat(subs2.getType()).isEqualTo(SubscriptionEnumType.DEFAULT);

    }

}
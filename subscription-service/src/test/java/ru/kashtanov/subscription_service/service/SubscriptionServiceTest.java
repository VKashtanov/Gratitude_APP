package ru.kashtanov.subscription_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kashtanov.subscription_service.dto.SubscriptionDto;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumType;
import ru.kashtanov.subscription_service.exceptions.SubscriptionCrudException;
import ru.kashtanov.subscription_service.model.Subscription;
import ru.kashtanov.subscription_service.repo.SubscriptionRepo;
import ru.kashtanov.subscription_service.util.SubscriptionBuilderService;
import ru.kashtanov.subscription_service.util.ValidationService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Viktor Кashtanov
 */
@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;
    @Mock
    private SubscriptionRepo repo;
    @Mock
    private ValidationService validationService;
    @Mock
    private SubscriptionBuilderService builderService;

    //Variables
    private SubscriptionDto requestDto;
    private SubscriptionDto responseDto;
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        requestDto = new SubscriptionDto();
        requestDto.setTargetId(1L);
        requestDto.setUserId(2L);
        requestDto.setType(SubscriptionEnumType.DEFAULT);

        responseDto = new SubscriptionDto();
        responseDto.setId(1L);
        responseDto.setTargetId(1L);
        responseDto.setUserId(2L);
        responseDto.setType(SubscriptionEnumType.DEFAULT);

        subscription = new Subscription();
        subscription.setId(1L);
        subscription.setTargetId(1L);
        subscription.setUserId(2L);
        subscription.setType(SubscriptionEnumType.DEFAULT);
    }

    @Test
    void create_When_ScenarioPositive_1() {
        // GIVEN
        Long targetId = requestDto.getTargetId();
        Long userId = requestDto.getUserId();
        SubscriptionEnumType type = requestDto.getType();

        // WHEN
        when(validationService.validateCommon(requestDto)).thenReturn(Boolean.TRUE);
        when(repo.existsByTargetIdAndUserId(targetId, userId)).thenReturn(Boolean.FALSE);
        when(builderService.buildSubscription(any(SubscriptionDto.class))).thenReturn(subscription);
        when(repo.save(any(Subscription.class))).thenReturn(subscription);
        when(builderService.buildDto(subscription)).thenReturn(responseDto);

        SubscriptionDto resultDto = subscriptionService.create(requestDto);

        // THEN
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getTargetId()).isEqualTo(targetId);
        assertThat(resultDto.getUserId()).isEqualTo(userId);
        assertThat(resultDto.getType()).isEqualTo(type);

        verify(validationService, times(1)).validateCommon(any(SubscriptionDto.class));
        verify(repo, times(1)).existsByTargetIdAndUserId(anyLong(), anyLong());
        verify(repo, times(1)).save(any(Subscription.class));
        verify(builderService, times(1)).buildSubscription(any(SubscriptionDto.class));
        verify(builderService, times(1)).buildDto(any(Subscription.class));

    }

    @Test
    void create_When_ScenarioAlreadyExists_2() {
        // GIVEN
        Long targetId = requestDto.getTargetId();
        Long userId = requestDto.getUserId();

        // WHEN
        when(validationService.validateCommon(requestDto)).thenReturn(Boolean.TRUE);
        when(repo.existsByTargetIdAndUserId(targetId, userId)).thenReturn(Boolean.TRUE);

        // THEN
        assertThatThrownBy(() -> subscriptionService.create(requestDto))
                .isInstanceOf(SubscriptionCrudException.class)
                .hasMessageContaining("Subscription already exists");

        verify(repo, times(1)).existsByTargetIdAndUserId(anyLong(), anyLong());
        verify(validationService, times(1)).validateCommon(any(SubscriptionDto.class));
        verify(repo, never()).save(any(Subscription.class));
        verify(builderService, never()).buildDto(any(Subscription.class));
        verify(builderService, never()).buildSubscription(any(SubscriptionDto.class));
    }

}
package ru.kashtanov.order_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.order_service.client.ProductServiceClient;
import ru.kashtanov.order_service.client.UserServiceClient;
import ru.kashtanov.order_service.dto.OrderDto;
import ru.kashtanov.order_service.dto.FullOrderInfo;
import ru.kashtanov.order_service.dto.OrderSaveDto;
import ru.kashtanov.order_service.dto.ProductDto;
import ru.kashtanov.order_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.order_service.enums.OrderStatus;
import ru.kashtanov.order_service.exception.OrderNotFoundException;
import ru.kashtanov.order_service.exception.OrderNotSavedException;
import ru.kashtanov.order_service.model.Order;
import ru.kashtanov.order_service.repo.OrderRepo;
import ru.kashtanov.order_service.util.OrderMapper;
import ru.kashtanov.order_service.util.ValidationService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;
    private final ValidationService validationService;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    public OrderService(OrderMapper orderMapper, OrderRepo orderRepo, ValidationService validationService, ProductServiceClient productServiceClient, UserServiceClient userServiceClient) {
        this.orderMapper = orderMapper;
        this.orderRepo = orderRepo;
        this.validationService = validationService;

        this.productServiceClient = productServiceClient;
        this.userServiceClient = userServiceClient;
    }

    // =============  CRUD ===================
    public OrderDto createOrder(OrderSaveDto dto) {
        if (!validationService.isValid(dto)) {
            throw new OrderNotSavedException("Invalid order data", HttpStatus.BAD_REQUEST);
        }
        Order order = orderMapper.toEntity(dto);
        order.setStatus(OrderStatus.PENDING);
//        order.setCreatedAt(Instant.from(LocalDateTime.now()));
        Order save = orderRepo.save(order);
        return orderMapper.toDto(save);
    }

    public List<OrderDto> getAllOrders() {
        List<Order> all = orderRepo.findAll();
        return all.stream()
                .map(orderMapper::toDto)
                .toList();

    }

    public OrderDto getOrderById(Long id) {
        Order foundProduct = orderRepo.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order is not found", HttpStatus.NOT_FOUND)
        );
        return orderMapper.toDto(foundProduct);
    }

    public void deleteOrderById(int id) {
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public OrderDto deleteProductById(Long id) {
        Order orderNotFound = orderRepo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found", HttpStatus.NOT_FOUND));
        orderRepo.delete(orderNotFound);
        return orderMapper.toDto(orderNotFound);
    }

    public FullOrderInfo provideOrderInfo(Long orderId) {
        var orderById = getOrderById(orderId);
        List<Long> productIds = orderById.getProductIds();
        List<ProductDto> productsByAPI = productServiceClient.getProductsByAPI(productIds);
        UserDtoResponseDetailed userDto = userServiceClient.fetchUserDto(orderById.getUserId());

        var fullOderDto = new FullOrderInfo();
        fullOderDto.setOrderId(orderId);
        fullOderDto.setProductIds(productIds);
        fullOderDto.setProducts(productsByAPI);
        fullOderDto.setUser(userDto);
        fullOderDto.setStatus(orderById.getStatus());
        fullOderDto.setCreatedAt(orderById.getCreatedAt());
        fullOderDto.setFinishedAt(orderById.getFinishedAt());
        return fullOderDto;
    }

//    public void updateOrder(OrderSaveDto dto) {
//    }


}

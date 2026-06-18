package com.netcore.cleanwave.platform.orders.interfaces.rest;

import com.netcore.cleanwave.platform.orders.application.commandservices.OrderCommandService;
import com.netcore.cleanwave.platform.orders.application.queryservices.OrderQueryService;
import com.netcore.cleanwave.platform.orders.domain.model.commands.UpdateOrderStatusCommand;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetAllOrdersQuery;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetOrderByIdQuery;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetOrdersByUserQuery;
import com.netcore.cleanwave.platform.orders.domain.model.valueobjects.OrderStatus;
import com.netcore.cleanwave.platform.orders.interfaces.rest.resources.CreateOrderResource;
import com.netcore.cleanwave.platform.orders.interfaces.rest.resources.OrderResource;
import com.netcore.cleanwave.platform.orders.interfaces.rest.resources.UpdateOrderStatusResource;
import com.netcore.cleanwave.platform.orders.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.orders.interfaces.rest.transform.OrderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller exposing orders endpoints.
 *
 * <p>Handles orders requests. Delegates logic to {@link OrderCommandService}
 * and {@link OrderQueryService} and translates domain results to HTTP responses.</p>
 */
@NullMarked
@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Orders", description = "Orders management endpoints")
public class OrdersController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    public OrdersController(OrderCommandService orderCommandService, OrderQueryService orderQueryService) {
        this.orderCommandService = orderCommandService;
        this.orderQueryService = orderQueryService;
    }

    /**
     * Creates a new order.
     *
     * @param resource the request body containing order details
     * @return {@code 201 Created} with the created order resource
     */
    @PostMapping
    public ResponseEntity<OrderResource> createOrder(@RequestBody CreateOrderResource resource) {
        var command = CreateOrderCommandFromResourceAssembler.toCommandFromResource(resource);
        Long orderId = orderCommandService.handle(command);

        var query = new GetOrderByIdQuery(orderId);
        var order = orderQueryService.handle(query);

        if (order.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var orderResource = OrderResourceFromEntityAssembler.toResourceFromEntity(order.get());
        return new ResponseEntity<>(orderResource, HttpStatus.CREATED);
    }

    /**
     * Gets all orders. If userId is provided as query param, filters by user.
     *
     * @param userId optional user ID to filter by
     * @return list of orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResource>> getOrders(@RequestParam(required = false) Long userId) {
        List<OrderResource> orderResources;
        
        if (userId != null) {
            var query = new GetOrdersByUserQuery(userId);
            var orders = orderQueryService.handle(query);
            orderResources = orders.stream()
                    .map(OrderResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        } else {
            var query = new GetAllOrdersQuery();
            var orders = orderQueryService.handle(query);
            orderResources = orders.stream()
                    .map(OrderResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(orderResources);
    }

    /**
     * Gets an order by its ID.
     *
     * @param orderId the order ID
     * @return the order resource
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResource> getOrderById(@PathVariable Long orderId) {
        var query = new GetOrderByIdQuery(orderId);
        var order = orderQueryService.handle(query);

        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var orderResource = OrderResourceFromEntityAssembler.toResourceFromEntity(order.get());
        return ResponseEntity.ok(orderResource);
    }

    /**
     * Updates an order's status.
     *
     * @param orderId the order ID
     * @param resource the request body containing the new status
     * @return the updated order resource
     */
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResource> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusResource resource) {
        
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(resource.status().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        var command = new UpdateOrderStatusCommand(orderId, newStatus);
        var updatedOrder = orderCommandService.handle(command);

        if (updatedOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var orderResource = OrderResourceFromEntityAssembler.toResourceFromEntity(updatedOrder.get());
        return ResponseEntity.ok(orderResource);
    }
}

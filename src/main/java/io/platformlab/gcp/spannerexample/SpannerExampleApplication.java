package io.platformlab.gcp.spannerexample;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * This solution is derived from:
 * https://codelabs.developers.google.com/codelabs/cloud-spring-spanner#0
 * https://github.com/shinemon/demo-spring-boot/tree/master/spring-boot-aop-aspectj
 *
 * Spanner docs:
 * https://cloud.google.com/spanner/docs
 */
@SpringBootApplication
public class SpannerExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpannerExampleApplication.class, args);
  }

  @RestController
  class OrderController {

    private final OrderRepository orderRepository;

    OrderController(OrderRepository orderRepository) {
      this.orderRepository = orderRepository;
    }

    @GetMapping("/hello")
    public String sayHello() {
      return "Hello!";
    }

    @TrackExecutionTime
    @GetMapping("/api/orders/{id}")
    public Order getOrder(@PathVariable String id) {
      return orderRepository
        .findById(id)
        .orElseThrow(
          () ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, id + " not found")
        );
    }

    @TrackExecutionTime
    @PostMapping("/api/orders")
    public String createOrder(@RequestBody Order order) {
      // Spanner currently does not auto generate IDs
      // Generate UUID on new orders
      order.setId(UUID.randomUUID().toString());
      order.setTimestamp(LocalDateTime.now());

      order
        .getItems()
        .forEach(
          item -> {
            // Assign parent ID, and also generate child ID
            item.setOrderId(order.getId());
            item.setOrderItemId(UUID.randomUUID().toString());
          }
        );

      Order saved = orderRepository.save(order);
      return saved.getId();
    }
  }
}

package io.platformlab.gcp.spannerexample;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import com.google.cloud.spring.data.spanner.core.mapping.*;

@Table(name="orders")
@Data
public class Order {
  @PrimaryKey
  @Column(name="order_id")
  private String id;

  private String description;

  @Column(name="creation_timestamp")
  private LocalDateTime timestamp;

  @Interleaved
  private List<OrderItem> items;
}
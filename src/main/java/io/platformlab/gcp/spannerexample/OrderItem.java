package io.platformlab.gcp.spannerexample;

import lombok.Data;
import com.google.cloud.spring.data.spanner.core.mapping.*;

@Table(name="order_items")
@Data
class OrderItem {
  @PrimaryKey(keyOrder = 1)
  @Column(name="order_id")
  private String orderId;

  @PrimaryKey(keyOrder = 2)
  @Column(name="order_item_id")
  private String orderItemId;

  private String description;
  private Long quantity;
}
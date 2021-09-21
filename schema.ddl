CREATE TABLE orders (
  order_id STRING(36) NOT NULL,
  description STRING(255),
  creation_timestamp TIMESTAMP,
) PRIMARY KEY (order_id);

CREATE TABLE order_items (
  order_id STRING(36) NOT NULL,
  order_item_id STRING(36) NOT NULL,
  description STRING(255),
  quantity INT64,
) PRIMARY KEY (order_id, order_item_id),
  INTERLEAVE IN PARENT orders ON DELETE CASCADE;
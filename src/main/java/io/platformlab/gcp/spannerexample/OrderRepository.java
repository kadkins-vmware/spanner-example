package io.platformlab.gcp.spannerexample;

import org.springframework.stereotype.*;
import com.google.cloud.spring.data.spanner.repository.*;

@Repository
public interface OrderRepository extends SpannerRepository<Order, String> {
}
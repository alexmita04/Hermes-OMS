package com.alexmita.ordermanagementsystem.domain.order;
import com.alexmita.ordermanagementsystem.domain.order.dtos.OrderCreatedEvent;
import com.alexmita.ordermanagementsystem.kafka.KafkaTopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventProducer.class);

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(KafkaTopicConfig.ORDER_CREATED_TOPIC,
                        String.valueOf(event.orderId()), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish order event for orderId={}", event.orderId(), ex);
                    } else {
                        log.info("Published order event for orderId={}", event.orderId());
                    }
                });
    }
}
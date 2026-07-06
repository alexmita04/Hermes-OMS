package com.alexmita.ordermanagementsystem.domain.order;

import com.alexmita.ordermanagementsystem.domain.order.dtos.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class OrderCsvLoggerConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderCsvLoggerConsumer.class);

    @Value("${app.csv-log.path:orders-log.csv}")
    private String csvPathString;

    @KafkaListener(topics = "order-created", groupId = "csv-logger-group")
    public synchronized void logOrder(OrderCreatedEvent event) {
        Path csvPath = Path.of(csvPathString);
        try {
            boolean fileExists = Files.exists(csvPath);

            try (FileWriter writer = new FileWriter(csvPath.toFile(), true)) {
                if (!fileExists) {
                    writer.write("orderId,productId,productName,quantity,createdAt\n");
                }
                for (var item : event.items()) {
                    writer.write(String.format("%d,%d,%s,%d,%s%n",
                            event.orderId(),
                            item.productId(),
                            item.productName().replace(",", " "),
                            item.quantity(),
                            event.createdAt()));
                }
            }
        } catch (IOException e) {
            log.error("Failed to write order {} to CSV", event.orderId(), e);
        }
    }
}
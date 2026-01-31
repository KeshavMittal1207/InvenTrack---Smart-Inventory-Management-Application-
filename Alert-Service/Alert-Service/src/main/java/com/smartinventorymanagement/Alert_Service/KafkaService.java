package com.smartinventorymanagement.Alert_Service;

import com.smartinventorymanagement.Alert_Service.Dto.AlertDto;
import com.smartinventorymanagement.Alert_Service.Model.Alert;
import com.smartinventorymanagement.Alert_Service.Service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaService {

    @Autowired
    private AlertService alertService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "low-stock", groupId = "inventory-group")
    public void listenLowStock(AlertDto alertDto) {
        Alert alert = Alert.builder()
                .alertType(alertDto.getAlertType())
                .date(alertDto.getDate())
                .itemId(alertDto.getItemId())
                .build();

        log.warn("Low Stock Alert: {}", alert.getAlertType());
        alertService.addAlert(alert);
        alertService.sendMail(alert);
        kafkaTemplate.send("stock-order",alert.getItemId());
    }

    @KafkaListener(topics = "near-expiry", groupId = "inventory-group")
    public void listenNearExpiry(AlertDto alertDto) {
        Alert alert = Alert.builder()
                .alertType(alertDto.getAlertType())
                .date(alertDto.getDate())
                .itemId(alertDto.getItemId())
                .build();

        log.warn("Near Expiry Alert: {}", alert.getAlertType());
        alertService.addAlert(alert);
        alertService.sendMail(alert);
    }
}
package com.smartinventorymanagement.Alert_Service;

import com.smartinventorymanagement.Alert_Service.Dto.AlertDto;
import com.smartinventorymanagement.Alert_Service.Model.Alert;
import com.smartinventorymanagement.Alert_Service.Service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaService {

    @Autowired
    private AlertService alertService;

    @KafkaListener(topics = "low-stock", groupId = "inventory-group")
    public void listenLowStock(AlertDto alertDto) {
        log.warn(alertDto.toString());
        if (alertService.alertExists(
            alertDto.getAlertType(),
            alertDto.getProductId(),
            alertDto.getBatchId()
        )) return; 

        Alert alert = Alert.builder()
                .alertType(alertDto.getAlertType())
                .batchId(alertDto.getBatchId())
                .productId(alertDto.getProductId())
                .build();

        log.warn("Low Stock Alert for product {}", alert.getProductId());

        alertService.addAlert(alert);
        alertService.sendMail(alert);
    }

    @KafkaListener(topics = "near-expiry", groupId = "inventory-group")
    public void listenNearExpiry(AlertDto alertDto) {
        log.warn(alertDto.toString());
        if (alertService.alertExists(
            alertDto.getAlertType(),
            alertDto.getProductId(),
            alertDto.getBatchId()
        )) return;

        Alert alert = Alert.builder()
                .alertType(alertDto.getAlertType())
                .productId(alertDto.getProductId())
                .batchId(alertDto.getBatchId())
                .build();

        log.warn("Near Expiry Alert for batch {}", alert.getBatchId());

        alertService.addAlert(alert);
        alertService.sendMail(alert);
    }
}
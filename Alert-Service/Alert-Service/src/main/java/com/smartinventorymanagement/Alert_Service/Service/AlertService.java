package com.smartinventorymanagement.Alert_Service.Service;

import com.smartinventorymanagement.Alert_Service.Dto.AlertResponseDto;
import com.smartinventorymanagement.Alert_Service.Model.Alert;
import com.smartinventorymanagement.Alert_Service.Repository.AlertRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public Alert addAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<AlertResponseDto> getRecentAlerts(){

        List<Alert> alerts = alertRepository.findTop5ByOrderByDateDesc();

        if (alerts.isEmpty()) {
            log.info("No recent alerts found");
            return Collections.emptyList();
        }

        return alerts
            .stream()
            .map((Alert alert) ->
                AlertResponseDto.builder()
                    .alertId(alert.getAlertId())
                    .alertType(alert.getAlertType())
                    .productId(alert.getProductId())
                    .batchId(alert.getBatchId())
                    .build()
            )
            .toList();     
    }

    public List<Alert> getAlert() {
        return alertRepository.findAll();
    }

    public List<Alert> getAlertByAlertType(String alertType) {
        return alertRepository.findAllByAlertType(alertType);
    }

    public void deleteAlertByAlertId(String alertId) {
        alertRepository.deleteByAlertId(alertId);
    }

    public boolean alertExists(
        String alertType,
        Long productId,
        Long batchId
    ) {
        return alertRepository
            .existsByAlertTypeAndProductIdAndBatchId(
                alertType, productId, batchId
            );
    }

    public void sendMail(Alert alert){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
        
            message.setSubject(alert.getAlertType());
            message.setTo("keshavmittal1207@gmail.com");
            message.setFrom("myqwery06@gmail.com");
            message.setText("REMINDER !!!!!\n" + "Product ID : " + alert.getProductId() + "\n" + "Batch ID : " + alert.getBatchId() + "\n"
                    + "Item Status : " + alert.getAlertType() + "\n"
                    + "Alert Date : " + alert.getDate() + "\n\n"
                    + "Thanks\n"
                    + "Smart Inventory Management System");
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error while Sending Email");
        }
    }
}

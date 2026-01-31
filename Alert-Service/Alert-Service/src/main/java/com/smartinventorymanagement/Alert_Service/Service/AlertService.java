package com.smartinventorymanagement.Alert_Service.Service;

import com.smartinventorymanagement.Alert_Service.Model.Alert;
import com.smartinventorymanagement.Alert_Service.Repository.AlertRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @CachePut(value = "Alerts_Cache" , key = "#result.alertId")
    @CacheEvict(value="Alerts_Cache" , key = "'allAlerts'")
    public Alert addAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    @Cacheable(value = "Alerts_Cache" , key = "'allAlerts'")
    public List<Alert> getAlert() {
        return alertRepository.findAll();
    }

    @Cacheable(value = "Alerts_Cache",key = "#alertType")
    public List<Alert> getAlertByAlertType(String alertType) {
        return alertRepository.findAllByAlertType(alertType);
    }

    @Caching(evict = {
        @CacheEvict(value = "Alerts_Cache", key = "#alertId"),
        @CacheEvict(value = "Alerts_Cache", key = "'allAlerts'")
    })
    public void deleteAlertByAlertId(String alertId) {
        alertRepository.deleteByAlertId(alertId);
    }

    public void sendMail(Alert alert){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(alert.getAlertType());
            message.setTo("keshavmittal1207@gmail.com");
            message.setFrom("myqwery06@gmail.com");
            message.setText("REMINDER !!!!!\n" + "Item ID : " + alert.getItemId() + "\n"
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

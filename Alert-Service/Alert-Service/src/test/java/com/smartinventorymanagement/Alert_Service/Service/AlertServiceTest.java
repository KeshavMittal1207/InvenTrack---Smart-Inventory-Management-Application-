package com.smartinventorymanagement.Alert_Service.Service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.smartinventorymanagement.Alert_Service.Model.Alert;
import com.smartinventorymanagement.Alert_Service.Repository.AlertRepository;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {

    @InjectMocks
    private AlertService alertService;

    private Alert alert;
    private Alert alertRequest;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    public void init(){
        alert = Alert.builder()
        .alertId("pqrs")
        .alertType("LOW_STOCK")
        .date(LocalDate.now())
        .itemId("abcd123")
        .build();

        alertRequest = Alert.builder()
        .alertType("LOW_STOCK")
        .date(LocalDate.now())
        .itemId("abcd123")
        .build();
    }

    @Test
    void addAlertTest(){
       
        when(alertRepository.save(alertRequest)).thenReturn(alert);
        Alert getSavedAlert = alertService.addAlert(alertRequest);

        assertNotNull(getSavedAlert);
        assertEquals(alert, getSavedAlert);
        assertEquals(alert.getAlertId(), getSavedAlert.getAlertId());

    }

    @Test
    void getAlertTest(){
        when(alertRepository.findAll()).thenReturn(List.of(alert));
        List<Alert> alerts = alertService.getAlert();
        
        assertNotNull(alerts);
        assertEquals(1, alerts.size());
        assertEquals(alerts, List.of(alert));
    }

   

    @Test
    void sendMailTest(){
        alertService.sendMail(alert);
        verify(javaMailSender , times(1)).send(any(SimpleMailMessage.class));
        assertDoesNotThrow(() -> javaMailSender.send(any(SimpleMailMessage.class)),"Error while Sending Email");
    }

    @Test
    void getAlertByAlertTypeTest(){
        String alertType = "LOW_STOCK";

        when(alertRepository.findAllByAlertType(alertType)).thenReturn(List.of(alert));
        List<Alert> alerts = alertService.getAlertByAlertType(alertType);

        assertNotNull(alerts);
        assertEquals(1, alerts.size());
        assertEquals(alerts.get(0).getAlertType(),alertType );
    }

    @Test
    void deleteAlertByAlertIdTest(){
        String alertId = "pqrs";

        alertService.deleteAlertByAlertId(alertId);

        verify(alertRepository , times(1)).deleteById(alertId);
    }

}

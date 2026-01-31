package com.smartinventorymanagement.Alert_Service.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.smartinventorymanagement.Alert_Service.Model.Alert;

@DataJpaTest
public class AlertRepositoryTest {

    @Autowired
    private AlertRepository alertRepository;

    private static Alert alert;

    @BeforeAll
    static void init(){
        alert = Alert.builder()
        .alertType("LOW_STOCK")
        .date(LocalDate.now())
        .itemId("abcd")
        .build();
    }

    @Test
    void ShouldReturnAlertsIfFoundByAlertType(){
        String alertType = "LOW_STOCK";

        Alert savedSeller = alertRepository.save(alert);

        List<Alert> alerts = alertRepository.findAllByAlertType(alertType);

        assertNotNull(alerts);
        assertEquals(List.of(savedSeller), alerts);
        assertEquals(alerts.get(0).getItemId(), "abcd");
        assertNotNull(alerts.get(0).getAlertId());
    }

    @Test
    void ShouldReturnNullIfAlertsNotFoundByAlertType(){
        alertRepository.save(alert);

        List<Alert> alerts = alertRepository.findAllByAlertType("NEAR_EXPIRY");

        assertEquals(alerts.isEmpty(),true);
    }

    @Test
    void ShouldDeleteAlertByAlertIdIfPresent(){
        Alert savedAlert = alertRepository.save(alert);

        alertRepository.deleteByAlertId(savedAlert.getAlertId());

        Optional<Alert> found = alertRepository.findById(savedAlert.getAlertId());

        assertTrue(found.isEmpty());
    }

}


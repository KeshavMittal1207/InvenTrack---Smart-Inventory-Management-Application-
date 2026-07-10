package com.smartinventorymanagement.Alert_Service.Controller;

import com.smartinventorymanagement.Alert_Service.Dto.AlertDto;
import com.smartinventorymanagement.Alert_Service.Dto.AlertResponseDto;
import com.smartinventorymanagement.Alert_Service.Model.Alert;
import com.smartinventorymanagement.Alert_Service.Service.AlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/getAlert")
    public List<Alert> getAlert() {
        return alertService.getAlert();
    }

    @GetMapping("getAlert/{alertType}")
    public List<Alert> getAlertByAlertType(@PathVariable String alertType) {
        return alertService.getAlertByAlertType(alertType);
    }

    @DeleteMapping("/deleteAlert/{alertId}")
    public void deleteAlertByAlertId(@PathVariable String alertId) {
        alertService.deleteAlertByAlertId(alertId);
    }

    @GetMapping("/recent")
    public List<AlertResponseDto> getRecentAlerts() {
        return alertService.getRecentAlerts();
    }

    @PostMapping("/create")
    public Alert createAlert(@RequestBody AlertDto alertDto) {
        Alert alert = Alert.builder()
                .alertType(alertDto.getAlertType())
                .productId(alertDto.getProductId())
                .batchId(alertDto.getBatchId())
                .build();
        return alertService.createAlert(alert);
    }
}

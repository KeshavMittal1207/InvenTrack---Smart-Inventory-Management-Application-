package com.smartinventorymanagement.Alert_Service.Controller;

import com.smartinventorymanagement.Alert_Service.Dto.AlertResponseDto;
import com.smartinventorymanagement.Alert_Service.Model.Alert;
import com.smartinventorymanagement.Alert_Service.Service.AlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/getAlert")
    public List<Alert> getAlert(){
        return alertService.getAlert();
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("getAlert/{alertType}")
    public List<Alert> getAlertByAlertType(@PathVariable String alertType){
        return alertService.getAlertByAlertType(alertType);
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @DeleteMapping("/deleteAlert/{alertId}")
    public void deleteAlertByAlertId(@PathVariable String alertId){
         alertService.deleteAlertByAlertId(alertId);
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/recent")
    public List<AlertResponseDto> getRecentAlerts(){
        return alertService.getRecentAlerts();
    }
}

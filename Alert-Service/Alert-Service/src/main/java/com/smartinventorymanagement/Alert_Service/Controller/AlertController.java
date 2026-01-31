package com.smartinventorymanagement.Alert_Service.Controller;

import com.smartinventorymanagement.Alert_Service.Model.Alert;
import com.smartinventorymanagement.Alert_Service.Service.AlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping("/addAlert")
    public ResponseEntity<Alert> addAlert(@RequestBody Alert alert){
        Alert alert1 = alertService.addAlert(alert);
        return ResponseEntity.ok(alert1);
    }
    @GetMapping("/getAlert")
    public List<Alert> getAlert(){
        return alertService.getAlert();
    }
    @GetMapping("getAlert/{alertType}")
    public List<Alert> getAlertByAlertType(@PathVariable String alertType){
        return alertService.getAlertByAlertType(alertType);
    }
    @DeleteMapping("/deleteAlert/{alertId}")
    public void deleteAlertByAlertId(@PathVariable String alertId){
         alertService.deleteAlertByAlertId(alertId);
    }
    @PostMapping("/sendMail")
    public void sendMail(@RequestBody Alert alert){
        alertService.sendMail(alert);
    }
}

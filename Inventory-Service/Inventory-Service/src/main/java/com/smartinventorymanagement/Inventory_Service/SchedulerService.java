package com.smartinventorymanagement.Inventory_Service;

import com.smartinventorymanagement.Inventory_Service.Dto.AlertDto;
import com.smartinventorymanagement.Inventory_Service.Model.Item;
import com.smartinventorymanagement.Inventory_Service.Repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SchedulerService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private KafkaTemplate<String, AlertDto> kafkaTemplate;

    @Scheduled(cron = "0 32 18 * * *")
    public void checkInventory() {
        log.warn("Inside Scheduled method");
        LocalDate today = LocalDate.now();
        Date expiryCutoff = Date.from(today.plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Item> items = itemRepository.findAll();

        for (Item item : items) {
            if (item.getQuantity() < item.getThresholdQuantity()) {
                AlertDto lowStockAlert = new AlertDto("LOW_STOCK", item.getItemId(), today);
                kafkaTemplate.send("low-stock", lowStockAlert);
            }

            if (item.getExpiryDate().before(expiryCutoff)) {
                log.warn("Item Expiry Log");
                AlertDto expiryAlert = new AlertDto("NEAR_EXPIRY", item.getItemId(), today);
                kafkaTemplate.send("near-expiry", expiryAlert);
            }
        }
    }
}

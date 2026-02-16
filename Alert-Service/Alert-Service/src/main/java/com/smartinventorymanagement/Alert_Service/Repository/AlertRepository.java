package com.smartinventorymanagement.Alert_Service.Repository;

import com.smartinventorymanagement.Alert_Service.Model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert , String> {
    List<Alert> findAllByAlertType(String alertType);

    void deleteByAlertId(String alertId);

    boolean existsByAlertTypeAndProductIdAndBatchId(
        String alertType,
        Long productId,
        Long batchId
);

List<Alert> findTop5ByOrderByDateDesc();

}

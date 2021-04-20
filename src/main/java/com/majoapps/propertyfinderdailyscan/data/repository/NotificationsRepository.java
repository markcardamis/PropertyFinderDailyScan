package com.majoapps.propertyfinderdailyscan.data.repository;

import com.majoapps.propertyfinderdailyscan.data.entity.Notifications;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends CrudRepository <Notifications, UUID> {
    @Query(value = "SELECT * FROM Notifications a WHERE a.frequency IS NOT NULL AND a.frequency NOT LIKE 'OFF' AND COALESCE(a.last_triggered_at, date '2001-01-01') + CAST(CONCAT('P1', LEFT(a.frequency,1)) as INTERVAL) <= NOW()", nativeQuery = true)
    List<Notifications> findAllToBeTriggered();
}

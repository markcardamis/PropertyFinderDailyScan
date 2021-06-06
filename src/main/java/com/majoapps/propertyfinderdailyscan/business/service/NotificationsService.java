package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.data.entity.Notifications;
import com.majoapps.propertyfinderdailyscan.data.repository.NotificationsRepository;
import com.majoapps.propertyfinderdailyscan.exception.ResourceNotFoundException;

import java.time.Instant;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;

    @Autowired
    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public List<Notifications> getOutstandingNotifications() {
        //where last triggered is null OR last_triggered + frequency*days < now
        try {
            List<Notifications> notifications = new ArrayList<>();
            Iterable<Notifications> results = this.notificationsRepository.findAllToBeTriggered();
            for (Notifications result : results) {
                notifications.add(result);
                result.setLastTriggeredAt(Date.from(Instant.now()));    // update last_triggered in notification
                this.notificationsRepository.save(result);              // save updated last_triggered time
            }
            return notifications;
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new ResourceNotFoundException("Error retrieving results");
        }
    }

    public Notifications getNotificationsById(UUID id) {
        Objects.requireNonNull(id);
        return this.notificationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Notifications " + id + " not found")));
    }
}

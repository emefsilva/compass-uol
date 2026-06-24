package br.com.compass.uol.service;

import br.com.compass.uol.dto.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @Async
    public void sendNotification(TransferResponse response) {

        try {
            log.info("Starting to send the notification for the transfer {}, with value {}",
                    response.transactionId(), response.amount());
            Thread.sleep(3000);

            log.info("Notification sent successfully! Origin: {} | Destination: {} | Amount: R$ {}",
                    response.senderId(), response.receiverId(), response.amount());
        }catch (InterruptedException e){
            log.info("Error to send the notification", e);
            Thread.currentThread().interrupt();
        }
    }
}

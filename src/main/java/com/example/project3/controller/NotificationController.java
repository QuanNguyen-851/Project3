package com.example.project3.controller;

import com.example.project3.model.dto.NotificationResponse;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.service.NotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notif")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @GetMapping("/getAll")
  private ResponseEntity<List<NotificationResponse>> getAll(
      @RequestParam(value = "isRead", required = false) Boolean isRead
  ) {
    return new ResponseEntity<>(notificationService.getAllNotification(isRead), HttpStatus.OK);
  }
  @GetMapping("/readNotification")
  private ResponseEntity<Long> readNotification(
      @RequestParam(value = "id", required = true) Long id
  ) {
    return new ResponseEntity<>(notificationService.readNotification(id), HttpStatus.OK);
  }

  @GetMapping("/countUnread")
  private ResponseEntity<Long> countNotification() {
    return new ResponseEntity<>(notificationService.countUnRead(), HttpStatus.OK);
  }


}

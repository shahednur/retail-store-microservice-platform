package com.shahed.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEvent {
    private String eventId;
    private String evenType;
    private Long timestamp;
    private Long userId;
}

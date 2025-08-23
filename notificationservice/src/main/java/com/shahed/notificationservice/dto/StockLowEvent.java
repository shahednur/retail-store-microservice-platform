package com.shahed.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StockLowEvent extends BaseEvent {
    private String productId;
    private String productName;
    private Integer currentStoke;
    private Integer threshold;
    private String category;
    private String supplierId;
}

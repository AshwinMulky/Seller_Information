package com.sentryc.seller.response.dto;

import java.util.UUID;

import com.sentryc.seller.model.enums.SellerState;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProducerSellerStateDTO {
    private UUID producerId;
    private String producerName;
    private SellerState sellerState;
    private UUID sellerId;

}

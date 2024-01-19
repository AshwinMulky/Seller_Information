package com.sentryc.seller.response.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellerDTO {

    private String sellerName;
    private String externalId;
    private String marketplaceId;
    private List<ProducerSellerStateDTO> producerSellerStates;
    
}


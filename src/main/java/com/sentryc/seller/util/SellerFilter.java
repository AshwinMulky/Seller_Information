package com.sentryc.seller.util;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerFilter {

    private String searchByName;
    private List<UUID> producerIds;
    private List<String> marketplaceIds;

}


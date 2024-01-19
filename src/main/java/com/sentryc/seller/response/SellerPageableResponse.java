package com.sentryc.seller.response;

import java.util.List;

import com.sentryc.seller.response.dto.SellerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerPageableResponse {

    private PageMeta meta;
    private List<SellerDTO> data;

}


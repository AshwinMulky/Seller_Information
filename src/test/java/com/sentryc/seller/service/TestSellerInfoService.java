package com.sentryc.seller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.sentryc.seller.model.Marketplace;
import com.sentryc.seller.model.Producer;
import com.sentryc.seller.model.Seller;
import com.sentryc.seller.model.SellerInfo;
import com.sentryc.seller.model.enums.SellerState;
import com.sentryc.seller.repository.SellerInfoRepository;
import com.sentryc.seller.response.SellerPageableResponse;
import com.sentryc.seller.response.dto.ProducerSellerStateDTO;
import com.sentryc.seller.response.dto.SellerDTO;
import com.sentryc.seller.util.SellerFilter;

@SpringBootTest
public class TestSellerInfoService {

    @Autowired
    private SellerInfoService sellerInfoService;

    @MockBean
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void testGetSellers() {
         Page<SellerInfo> page = getResponsePage();
         when(sellerInfoRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
         SellerFilter filter = getFilter();
         SellerPageableResponse response = sellerInfoService.getSellers(filter, PageRequest.of(1, 10), Sort.unsorted());

         assertNotNull(response);
         assertNotNull(response.getMeta());
         assertNotNull(response.getData());
         assertNotNull(response.getData().get(0).getProducerSellerStates());

         assertEquals(1, response.getData().size());
         assertEquals(1, response.getData().get(0).getProducerSellerStates().size());

         SellerDTO sellerDTO = response.getData().get(0);
         assertEquals("Amazon Us", sellerDTO.getSellerName());
         assertEquals("A2QUTRSO1ZHRN9", sellerDTO.getExternalId());
         assertEquals("amazon.ae", sellerDTO.getMarketplaceId());

         ProducerSellerStateDTO producerSellerStateDTO = sellerDTO.getProducerSellerStates().get(0);
         assertEquals("6b2520b1-3695-450b-9e66-39ec4356a97c", producerSellerStateDTO.getProducerId().toString());
         assertEquals("adidas", producerSellerStateDTO.getProducerName());
         assertEquals("BLOCKLISTED", producerSellerStateDTO.getSellerState().name());
         assertEquals("057cd7ba-4088-4ff1-be69-c4f4847ed8a8", producerSellerStateDTO.getSellerId().toString());
    }

    private SellerFilter getFilter() {
        SellerFilter filter = new SellerFilter("Amaz", 
              List.of(UUID.fromString("6b2520b1-3695-450b-9e66-39ec4356a97c")), 
              List.of("ae"));
        return filter;
    }

    private Page<SellerInfo> getResponsePage() {
        List<SellerInfo> sellerList = new ArrayList<>();
         SellerInfo sellerInfo = new SellerInfo();
         sellerInfo.setName("Amazon Us");
         sellerInfo.setExternalId("A2QUTRSO1ZHRN9");

         Marketplace marketplace = new Marketplace();
         marketplace.setId("amazon.ae");
         sellerInfo.setMarketplace(marketplace);

         List<Seller> sellers = new ArrayList<>();
         Seller seller = new Seller();
         seller.setId(UUID.fromString("057cd7ba-4088-4ff1-be69-c4f4847ed8a8"));
         seller.setState(SellerState.BLOCKLISTED);
         Producer producer = new Producer();
         producer.setId(UUID.fromString("6b2520b1-3695-450b-9e66-39ec4356a97c"));
         producer.setName("adidas");
         seller.setProducer(producer);
         sellers.add(seller);
         sellerInfo.setSellers(sellers);
         sellerList.add(sellerInfo);
         Page<SellerInfo> page= new PageImpl<>(sellerList, PageRequest.of(1, 10, Sort.unsorted()), 1);
        return page;
    }
    
}

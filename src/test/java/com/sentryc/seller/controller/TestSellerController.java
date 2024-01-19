package com.sentryc.seller.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentryc.seller.model.enums.SellerState;
import com.sentryc.seller.response.PageMeta;
import com.sentryc.seller.response.SellerPageableResponse;
import com.sentryc.seller.response.dto.ProducerSellerStateDTO;
import com.sentryc.seller.response.dto.SellerDTO;
import com.sentryc.seller.service.SellerInfoService;
import com.sentryc.seller.util.SellerFilter;

@SpringBootTest
public class TestSellerController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SellerInfoService sellerInfoService;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetSellersApi() throws Exception {
        SellerPageableResponse mockResponse = getResponse();
        when(sellerInfoService.getSellers(any(SellerFilter.class), any(Pageable.class), any(Sort.class)))
                .thenReturn(mockResponse);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(
                        "/api/sellers?searchByName=Amaz&producerIds=6b2520b1-3695-450b-9e66-39ec4356a97c&marketplaceIds=ae"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        SellerPageableResponse res = om.readValue(response, SellerPageableResponse.class);

        assertNotNull(res);
        assertNotNull(res.getData());
        assertEquals(1, res.getData().size());

        SellerDTO sellerDTO = res.getData().get(0);
        assertEquals("Amazon Us", sellerDTO.getSellerName());
        assertEquals("A2QUTRSO1ZHRN9", sellerDTO.getExternalId());
        assertEquals("amazon.ae", sellerDTO.getMarketplaceId());

        ProducerSellerStateDTO producerSellerStateDTO = sellerDTO.getProducerSellerStates().get(0);
        assertEquals("6b2520b1-3695-450b-9e66-39ec4356a97c", producerSellerStateDTO.getProducerId().toString());
        assertEquals("adidas", producerSellerStateDTO.getProducerName());
        assertEquals("BLOCKLISTED", producerSellerStateDTO.getSellerState().name());
        assertEquals("057cd7ba-4088-4ff1-be69-c4f4847ed8a8", producerSellerStateDTO.getSellerId().toString());
    }

    private SellerPageableResponse getResponse() {
        PageMeta meta = new PageMeta(1, 1, 1, 10);

        List<SellerDTO> data = new ArrayList<>();
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setSellerName("Amazon Us");
        sellerDTO.setExternalId("A2QUTRSO1ZHRN9");
        sellerDTO.setMarketplaceId("amazon.ae");

        List<ProducerSellerStateDTO> producerSellerStates = new ArrayList<>();
        ProducerSellerStateDTO producerSellerStateDTO = new ProducerSellerStateDTO();
        producerSellerStateDTO.setProducerId(UUID.fromString("6b2520b1-3695-450b-9e66-39ec4356a97c"));
        producerSellerStateDTO.setProducerName("adidas");
        producerSellerStateDTO.setSellerState(SellerState.BLOCKLISTED);
        producerSellerStateDTO.setSellerId(UUID.fromString("057cd7ba-4088-4ff1-be69-c4f4847ed8a8"));
        producerSellerStates.add(producerSellerStateDTO);

        sellerDTO.setProducerSellerStates(producerSellerStates);
        data.add(sellerDTO);
        SellerPageableResponse response = new SellerPageableResponse(meta, data);

        return response;
    }

}

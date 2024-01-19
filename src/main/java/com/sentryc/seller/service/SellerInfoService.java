package com.sentryc.seller.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sentryc.seller.model.Seller;
import com.sentryc.seller.model.SellerInfo;
import com.sentryc.seller.repository.SellerInfoRepository;
import com.sentryc.seller.response.PageMeta;
import com.sentryc.seller.response.SellerPageableResponse;
import com.sentryc.seller.response.dto.ProducerSellerStateDTO;
import com.sentryc.seller.response.dto.SellerDTO;
import com.sentryc.seller.util.SellerFilter;
import com.sentryc.seller.util.SellerSpecification;

@Service
public class SellerInfoService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    public SellerPageableResponse getSellers(SellerFilter filter, Pageable pageable, Sort sort) {
        Specification<SellerInfo> spec = SellerSpecification.build(filter);
        Page<SellerInfo> sellers = sellerInfoRepository.findAll(spec,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        System.out.println(sellers);
        List<SellerInfo> sellerList = sellers.getContent();
        List<SellerDTO> sellerDTOList = sellerList.stream().map(this::convertToDTO).collect(Collectors.toList());

        return new SellerPageableResponse(new PageMeta(sellers.getTotalElements(), sellers.getTotalPages(),
                sellers.getNumber() + 1, sellers.getSize()), sellerDTOList);
    }

    private SellerDTO convertToDTO(SellerInfo sellerInfo) {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setExternalId(sellerInfo.getExternalId());
        sellerDTO.setMarketplaceId(sellerInfo.getMarketplace().getId());
        sellerDTO.setSellerName(sellerInfo.getName());

        List<ProducerSellerStateDTO> producerSellerStates = sellerInfo.getSellers().stream()
                .map(this::convertToProducerSellerStateDTO)
                .collect(Collectors.toList());

        sellerDTO.setProducerSellerStates(producerSellerStates);

        return sellerDTO;
    }

    private ProducerSellerStateDTO convertToProducerSellerStateDTO(Seller seller) {
        ProducerSellerStateDTO producerSellerStateDTO = new ProducerSellerStateDTO();
        producerSellerStateDTO.setProducerId(seller.getProducer().getId());
        producerSellerStateDTO.setProducerName(seller.getProducer().getName());
        producerSellerStateDTO.setSellerState(seller.getState());
        producerSellerStateDTO.setSellerId(seller.getId());

        return producerSellerStateDTO;
    }

}

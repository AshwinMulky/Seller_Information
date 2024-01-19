package com.sentryc.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sentryc.seller.model.enums.SellerSortBy;
import com.sentryc.seller.request.PageInput;
import com.sentryc.seller.response.SellerPageableResponse;
import com.sentryc.seller.service.SellerInfoService;
import com.sentryc.seller.util.SellerFilter;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @GetMapping
    public ResponseEntity<SellerPageableResponse> getSellers(@ModelAttribute SellerFilter filter,
            @ModelAttribute PageInput pageInput,
            @RequestParam(required = false) SellerSortBy sortBy) {
        Sort sort = (sortBy != null) ? SellerSortBy.getSort(sortBy) : Sort.unsorted();
        Pageable pageable = PageRequest.of(pageInput.getPage(), pageInput.getSize(), sort);
        SellerPageableResponse response = sellerInfoService.getSellers(filter, pageable, sort);
        return ResponseEntity.ok(response);
    }
}

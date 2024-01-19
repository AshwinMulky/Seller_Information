package com.sentryc.seller.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sentryc.seller.model.SellerInfo;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, UUID>, JpaSpecificationExecutor<SellerInfo> {

}


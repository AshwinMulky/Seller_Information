package com.sentryc.seller.model;

import java.util.UUID;

import com.sentryc.seller.model.enums.SellerState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "sellers", schema = "public", indexes = {
        @Index(name = "idx_producer_id", columnList = "producer_id")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = { "producer_id", "seller_info_id", "state" })
})
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_info_id")
    private SellerInfo sellerInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 255)
    private SellerState state = SellerState.WHITELISTED;

}

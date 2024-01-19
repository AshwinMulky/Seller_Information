package com.sentryc.seller.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "seller_infos", schema = "public", indexes = {
        @Index(name = "idx_seller_name", columnList = "name"),
        @Index(name = "idx_seller_info_marketplace_id", columnList = "marketplace_id"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = { "marketplace_id", "external_id" })
})
public class SellerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marketplace_id", nullable = false)
    private Marketplace marketplace;

    @Column(name = "name", nullable = false, length = 2048)
    private String name;

    @Column(name = "url", length = 2048)
    private String url;

    @Column(name = "country")
    private String country;

    @Column(name = "external_id")
    private String externalId;

    @OneToMany(mappedBy = "sellerInfo")
    private List<Seller> sellers;

}

package com.sentryc.seller.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "marketplaces", schema = "public")
public class Marketplace {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "marketplace")
    private List<SellerInfo> sellerInfos;

}

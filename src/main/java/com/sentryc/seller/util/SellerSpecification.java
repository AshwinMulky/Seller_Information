package com.sentryc.seller.util;

import org.springframework.data.jpa.domain.Specification;

import com.sentryc.seller.model.SellerInfo;

public class SellerSpecification {

    public static Specification<SellerInfo> build(SellerFilter filter) {
        Specification<SellerInfo> spec = Specification.where(null);

        if (filter != null) {
            if (filter.getSearchByName() != null) {    
                spec = spec.and((root, query, cb) ->
                        cb.like(root.get("name"), "%" + filter.getSearchByName().toLowerCase() + "%"));
            }

            if (filter.getProducerIds() != null && !filter.getProducerIds().isEmpty()) {
                spec = spec.and((root, query, cb) -> root.get("sellers").get("producer").get("id").in(filter.getProducerIds()));
            }

            if (filter.getMarketplaceIds() != null && !filter.getMarketplaceIds().isEmpty()) {
                spec = spec.and((root, query, cb) -> root.get("marketplace").get("id").in(filter.getMarketplaceIds()));
            }
        }

        return spec;
    }
}


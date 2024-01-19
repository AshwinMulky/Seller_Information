package com.sentryc.seller.model.enums;

import org.springframework.data.domain.Sort;

public enum SellerSortBy {
    SELLER_INFO_EXTERNAL_ID_ASC,
    SELLER_INFO_EXTERNAL_ID_DESC,
    NAME_ASC,
    NAME_DESC,
    MARKETPLACE_ID_ASC,
    MARKETPLACE_ID_DESC;

    public static Sort getSort(SellerSortBy sortBy) {
        switch (sortBy) {
            case SELLER_INFO_EXTERNAL_ID_ASC:
                return Sort.by("externalId").ascending();
            case SELLER_INFO_EXTERNAL_ID_DESC:
                return Sort.by("externalId").descending();
            case NAME_ASC:
                return Sort.by("name").ascending();
            case NAME_DESC:
                return Sort.by("name").descending();
            case MARKETPLACE_ID_ASC:
                return Sort.by("marketplaceId").ascending();
            case MARKETPLACE_ID_DESC:
                return Sort.by("marketplaceId").descending();
            default:
                return Sort.unsorted();
        }
    }
}


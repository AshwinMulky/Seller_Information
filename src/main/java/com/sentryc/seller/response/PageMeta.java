package com.sentryc.seller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageMeta {

    private long totalCount;
    private int totalPages;
    private int currentPage;
    private int pageSize;
  
}

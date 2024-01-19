package com.sentryc.seller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInput {

    private int page = 0;
    private int size = 10;

}


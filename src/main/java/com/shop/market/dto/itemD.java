package com.shop.market.dto;

import lombok.*;

@Builder
@Getter
public class itemD {

    private Long id;
    private String seller;
    private String itemName;
    private int itemPrice;
    private int sellCount;
    private int itemStock;
}

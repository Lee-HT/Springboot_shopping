package com.shop.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class itemD {

    private Long id;
    private String seller;
    private String itemName;
    private int itemPrice;
    private int sellCount;
    private int itemStock;
}

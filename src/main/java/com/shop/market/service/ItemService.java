package com.shop.market.service;

import com.shop.market.dto.itemD;
import java.util.List;

public interface ItemService {
    List<itemD> searchItem(String itemName);

    void deleteItem(Long id);
}

package com.shop.market.service;

import com.shop.market.dto.itemD;
import java.util.List;

public interface ItemService {
    List<itemD> searchByItemName(String itemName);

    itemD searchById(Long id);

    itemD updateItem(itemD itemD);

    void insertItem(itemD item);

    void deleteItem(Long id);
}

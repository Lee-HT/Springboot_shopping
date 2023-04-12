package com.shop.market.repository;

import com.shop.market.dto.itemD;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
    List<itemD> searchItemsByItemName(String itemName);
    itemD selectItemByItemName(String itemName);
    itemD searchItemById(Long id);
    void updateItem(itemD item);
    void insertItem(itemD item);
    void deleteItemById(Long id);

}

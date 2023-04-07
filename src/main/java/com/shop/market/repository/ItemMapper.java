package com.shop.market.repository;

import com.shop.market.dto.itemD;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
    List<itemD> searchItemByItemName(String itemName);
    void deleteItemById(Long id);

}

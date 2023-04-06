package com.shop.market.service.Impl;

import com.shop.market.dto.itemD;
import com.shop.market.repository.ItemMapper;
import com.shop.market.service.ItemService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public List<itemD> searchItem(String itemName){
        log.info("itemService SearchItem");
        return itemMapper.searchItemByItemName(itemName);
    }
    @Override
    public void deleteItem(Long id){
        log.info("itemService deleteItem");
        try{
            itemMapper.deleteItemById(id);
        }catch(Exception e){
            log.info("deleteItem error" + e);
        }
    }
}

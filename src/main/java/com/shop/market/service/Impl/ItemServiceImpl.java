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
    public List<itemD> searchByItemName(String itemName){
        log.info("itemService SearchByItemName");
        return itemMapper.searchItemsByItemName(itemName + "%");
    }

    @Override
    public itemD searchById(Long id){
        log.info("itemService searchByID");
        return itemMapper.searchItemById(id);
    }

    @Override
    public void insertItem(itemD item){
        log.info("itemService insertItem");
        try{
            itemMapper.insertItem(item);
        }catch(Exception e){
            log.info("insertItem error: " + e);
        }
    }

    @Override
    public void deleteItem(Long id){
        log.info("itemService deleteItem");
        try{
            itemMapper.deleteItemById(id);
        }catch(Exception e){
            log.info("deleteItem error: " + e);
        }
    }

    @Override
    public itemD updateItem(itemD item){
        log.info("itemService updateItem");
        try{
            log.info(item.getItemName());
            itemD newItem = itemMapper.searchItemByItemName(item.getItemName());
            Long id = newItem.getId();
            log.info(id.toString());
            itemMapper.updateItem(itemD.builder().id(id)
                    .seller(item.getSeller())
                    .itemName(item.getItemName())
                    .itemPrice(item.getItemPrice())
                    .sellCount(item.getSellCount())
                    .itemStock(item.getItemStock())
                    .build());
            log.info(itemMapper.searchItemById(id).getItemName());
            return itemMapper.searchItemById(id);
        }catch (Exception e){
            log.info("updateItem error: " + e);
            return null;
        }
    }
}

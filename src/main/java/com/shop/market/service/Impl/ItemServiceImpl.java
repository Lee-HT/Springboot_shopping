package com.shop.market.service.Impl;

import com.shop.market.dto.itemD;
import com.shop.market.repository.ItemMapper;
import com.shop.market.service.ItemService;
import java.util.List;
import java.util.Map;
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
    public itemD updateItem(Map<String,Object> item){
        log.info("itemService updateItem");
        try{
            log.info(item.get("itemName").toString());
            itemD beforeItem = itemMapper.searchItemByItemName(item.get("itemName").toString());
            Long id = beforeItem.getId();
            log.info(id.toString());

            itemMapper.updateItem(itemD.builder().id(id)
                    .seller(beforeItem.getSeller())
                    .itemName(item.get("itemName").toString())
                    .itemPrice(Integer.parseInt(item.get("itemPrice").toString()))
                    .sellCount(beforeItem.getSellCount())
                    .itemStock(Integer.parseInt(item.get("itemStock").toString()))
                    .build());
            log.info(itemMapper.searchItemById(id).getItemName());
            return itemMapper.searchItemById(id);
        }catch (Exception e){
            log.info("updateItem error: " + e);
            return null;
        }
    }
}

package com.shop.market.controller;

import com.shop.market.dto.itemD;
import com.shop.market.service.ItemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("item")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("")
    @ResponseBody
    public List<itemD> selectItem(@RequestParam String itemName){
        log.info("itemController searchItem");
        log.info(itemName);
        return itemService.searchByItemName(itemName);
    }

    @PostMapping("")
    @ResponseBody
    public itemD insertItem(@RequestBody itemD item){
        log.info("itemController insertItem");
        log.info(item.getItemName());
        log.info(item.getSeller());
        itemService.insertItem(item);
        return item;
    }

    @DeleteMapping("")
    public String deleteItem(@RequestBody HashMap<String,Long> deleteMap){
        log.info("itemController deleteItem");
        Long id = deleteMap.get("id");
        itemService.deleteItem(id);
        return "redirect:/";
    }

    @PatchMapping("")
    @ResponseBody
    public itemD updateItem(@RequestBody Map<String,Object> item){
        log.info("itemController updateItem");
        log.info(item.get("itemName").toString());
        return itemService.updateItem(item);
    }

}

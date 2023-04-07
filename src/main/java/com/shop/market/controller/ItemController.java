package com.shop.market.controller;

import com.shop.market.dto.itemD;
import com.shop.market.service.ItemService;
import com.shop.market.service.PostService;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("item/")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("searchItem")
    @ResponseBody
    public List<itemD> searchItem(@RequestParam String itemName){
        log.info("itemController");
        log.info(itemName);
        return itemService.searchItem(itemName);
    }

    @DeleteMapping("deleteItem")
    public String deleteItem(@RequestBody HashMap<String,Long> deleteMap){
        log.info("itemController deleteItem");
        Long id = deleteMap.get("id");
        itemService.deleteItem(id);
        return "redirect:/";
    }

}

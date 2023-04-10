package com.shop.market.controller;

import com.shop.market.dto.itemD;
import com.shop.market.service.ItemService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("item/")
public class ItemViewController {

    @Autowired
    public ItemService itemService;

    @GetMapping("search/{searchText}")
    public String SearchItemView(@PathVariable String searchText, Model model){
        log.info("itemView search");
        List<itemD> searchList = null;
        if(searchText == null){
            searchList = itemService.searchByItemName(searchText);
        }else{
            log.info("searchText == null");
        }

        model.addAttribute("searchText",searchText);
        model.addAttribute("searchList",searchList);
        return "item/searchItem";
    }
}

package com.shop.market.controller;

import com.shop.market.dto.itemD;
import com.shop.market.service.ItemService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("itemV")
public class ItemViewController {

    @Autowired
    public ItemService itemService;

    @GetMapping("")
    public String SearchItem(@RequestParam(defaultValue = "") String searchText, Model model) {
        log.info("itemView search");
        List<itemD> searchList = null;
        log.info("searchText : " + searchText);
        if (!(searchText == null || searchText.equals(""))) {
            searchList = itemService.searchByItemName(searchText);
            if (searchList.isEmpty()) {
                log.info("searchList : empty");
            }
        } else {
        }

        model.addAttribute("searchText", searchText);
        model.addAttribute("searchList", searchList);
        return "item/searchItem";
    }

    @GetMapping("updatePage")
    public String updateItem(
            @RequestParam(required = false) Map<String,Object> item, Model model) {
        model.addAttribute("itemName", item.get("itemName"));
        model.addAttribute("itemPrice", item.get("itemPrice"));
        model.addAttribute("itemStock", item.get("itemStock"));

        return "item/itemModify";
    }
}

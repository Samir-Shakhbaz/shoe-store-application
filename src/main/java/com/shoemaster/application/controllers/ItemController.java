package com.shoemaster.application.controllers;

import com.shoemaster.application.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    public String displayItems(Model model) {
//        model.addAttribute("items", itemService.getAllItems());
        return "item-list";
    }

}

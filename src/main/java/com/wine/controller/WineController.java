package com.wine.controller;

import com.wine.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by erickang on 10/19/16.
 */
@RequestMapping("/wine")
@Controller
public class WineController {
    private WineService wineService;

    @Autowired
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

    @RequestMapping({"/list", "/"})
    public String listWines(Model model) {
//        System.out.print("=========== " +wineService.listAll().toString());
        model.addAttribute("wines", wineService.listAll());
        return "wine/list";
    }
}

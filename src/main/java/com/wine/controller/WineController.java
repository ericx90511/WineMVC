package com.wine.controller;

import com.wine.domain.Wine;
import com.wine.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        model.addAttribute("wines", wineService.listAll());
        return "wine/list";
    }

    @RequestMapping("/show/{id}")
    public String showWine(@PathVariable Integer id, Model model) {
        model.addAttribute("wine", wineService.getById(id));
        return "wine/show";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("wine", wineService.getById(id));
        return "wine/wineform";
    }

    @RequestMapping("/new")
    public String newWine(Model model) {
        model.addAttribute("wine", new Wine());
        return "wine/wineform";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(Wine wine) {
        Wine savedWine = wineService.saveOrUpdate(wine);
        return "redirect:wine/show/" + savedWine.getId();
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        wineService.delete(id);
        return "redirect:/wine/list";
    }
}

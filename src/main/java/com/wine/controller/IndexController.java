package com.wine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by erickang on 10/19/16.
 */
@Controller
public class IndexController {

    @RequestMapping({"/", ""})
    public String index() {
        return "index";
    }
}

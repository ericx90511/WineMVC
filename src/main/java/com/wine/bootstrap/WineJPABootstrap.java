package com.wine.bootstrap;

import com.wine.domain.Wine;
import com.wine.service.WineService;
import com.wine.util.WineJsonResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.wine.util.WineGenerator.getWines;
/**
 * Created by erickang on 10/17/16.
 */
@Component
public class WineJPABootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private WineService wineService;

    @Autowired
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Wine> wines = getWines(10);
        for (Wine wine : wines) {
            wineService.saveOrUpdate(wine);
        }
        try {
            WineJsonResolver.readJsonFile();
        }catch (IOException i)
        {
            System.out.print(i);
        }
    }
}

package com.wine.bootstrap;

import com.google.common.base.Throwables;
import com.wine.domain.Wine;
import com.wine.service.WineService;
import com.wine.util.WineJsonResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.wine.util.WineGenerator.getWines;
import static com.wine.util.WineLogResolver.addConsoleAppender;
import static com.wine.util.WineLogResolver.addLogger;
import static com.wine.util.WineLogResolver.setLogLevel;
/**
 * Created by erickang on 10/17/16.
 */
@Component
public class WineJPABootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private static final Logger log = LogManager.getLogger(WineJPABootstrap.class);
    private WineService wineService;

    @Autowired
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addConsoleAppender("Console");
        addLogger("com.wine", "Console");
        setLogLevel("com.wine", "DEBUG");

        try {
            List<Wine> wines = WineJsonResolver.readJsonFile();

            for (Wine wine : wines) {
                wineService.saveOrUpdate(wine);
            }
        }catch (IOException i) {
            log.error("failed to read Json file", i);
            Throwables.propagate(i);
        }catch (Throwable t) {
            log.error("unexpected error", t);
            Throwables.propagate(t);
        }

    }
}

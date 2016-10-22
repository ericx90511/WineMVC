package com.wine.util;

import com.wine.domain.Wine;
import com.wine.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
/**
 * Created by erickang on 10/17/16.
 */
public class WineGenerator {

    private WineGenerator() {}

    public static List<Wine> getWines(int numWines) {
        checkArgument(numWines > 0);
        List<Wine> wines = new ArrayList<>(numWines);
        for (int i = 0; i < numWines; i++) {
            wines.add(getRandomWine());
        }
        return wines;
    }

    private static Wine getRandomWine() {
        Wine wine = new Wine();
        wine.setName("good wine");
        return wine;
    }
}

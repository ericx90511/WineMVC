package com.wine.service;

import com.wine.domain.Wine;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created by erickang on 10/17/16.
 */
@Service
@Profile("jpadao")
public class WineService {
    private EntityManagerFactory entityFactory;

    @PersistenceUnit
    public void setEntityFactory(EntityManagerFactory entityFactory) {
        this.entityFactory = entityFactory;
    }


    public List<Wine> listAll() {
        EntityManager entityManager = entityFactory.createEntityManager();
        return entityManager.createQuery("from Wine", Wine.class).getResultList();
    }

    public Wine getById(Integer id) {
        EntityManager entityManager = entityFactory.createEntityManager();
        return entityManager.find(Wine.class, id);
    }

    public Wine saveOrUpdate(Wine wine) {
        EntityManager entityManager = entityFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Wine savedWine = entityManager.merge(wine);
        entityManager.getTransaction().commit();
        return savedWine;
    }

    public void delete(Integer id) {
        EntityManager entityManager = entityFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Wine.class, id));
        entityManager.getTransaction().commit();
    }
}

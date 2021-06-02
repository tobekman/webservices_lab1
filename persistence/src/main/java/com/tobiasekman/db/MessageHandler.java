package com.tobiasekman.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MessageHandler {


    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");


    public void add(Message message) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(message);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    public List<Message> getAll() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Message> messages = entityManager.createQuery("SELECT m FROM Message m").getResultList();
        entityManager.close();
        return messages;

    }

}

package org.okten;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class JpaProvider {

    private final EntityManagerFactory entityManagerFactory;

    public void doInJpa(Consumer<EntityManager> action) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();

            action.accept(entityManager);

            entityTransaction.commit();
        }
    }
}

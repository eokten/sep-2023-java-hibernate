package org.okten;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.okten.entity.Address;
import org.okten.entity.Order;
import org.okten.entity.OrderItem;
import org.okten.entity.OrderItemId;
import org.okten.entity.Product;
import org.okten.entity.Tag;
import org.okten.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    private static JpaProvider jpaProvider;

    public static void main(String[] args) {
        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ProductDatabase")) {
            jpaProvider = new JpaProvider(entityManagerFactory);

            jpaProvider.doInJpa(entityManager -> {
                User user = new User();
                user.setName("John");

                Address address = new Address();
                address.setCity("Lviv");
                address.setStreet("Sadova");
                address.setBuilding("123B");

                user.setAddress(address);

                entityManager.persist(address);
                entityManager.persist(user);
            });

            jpaProvider.doInJpa(entityManager -> {
                Product product1 = new Product();
                product1.setName("Product 1");
                product1.setPrice(1000);

                Product product2 = new Product();
                product2.setName("Product 2");
                product2.setPrice(1500);

                Product product3 = new Product();
                product3.setName("Product 3");
                product3.setPrice(3000);

                Order order = new Order();

                order.setOrderDate(LocalDateTime.now());

                OrderItem orderItem1 = new OrderItem();
                OrderItemId orderItemId1 = new OrderItemId();
                orderItemId1.setOrder(order);
                orderItemId1.setProduct(product1);
                orderItem1.setId(orderItemId1);
                orderItem1.setComment("Червоне упакування");
                orderItem1.setQuantity(3);

                OrderItem orderItem2 = new OrderItem();
                OrderItemId orderItemId2 = new OrderItemId();
                orderItemId2.setOrder(order);
                orderItemId2.setProduct(product2);
                orderItem2.setId(orderItemId2);
                orderItem2.setComment("Зелене упакування");
                orderItem2.setQuantity(10);

                entityManager.persist(product1);
                entityManager.persist(product2);
                entityManager.persist(product3);

                entityManager.persist(order);

                entityManager.persist(orderItem1);
                entityManager.persist(orderItem2);
            });

            jpaProvider.doInJpa(entityManager -> {
                Product product1 = entityManager
                        .createQuery("from Product where name = 'Product 1'", Product.class)
                        .getSingleResult();

                Tag tag1 = new Tag();
                tag1.setName("tag 1");

                Tag tag2 = new Tag();
                tag2.setName("tag 2");

                Tag tag3 = new Tag();
                tag3.setName("tag 3");

                product1.setTags(List.of(tag1, tag2));

                entityManager.persist(tag1);
                entityManager.persist(tag2);
                entityManager.persist(tag3);

                entityManager.persist(product1);
            });

            jpaProvider.doInJpa(entityManager -> {
                Product product1 = entityManager
                        .createQuery("from Product where name = 'Product 1'", Product.class)
                        .getSingleResult();

                entityManager.remove(product1);
            });

            jpaProvider.doInJpa(entityManager -> {
                Product product2 = entityManager
                        .createQuery("from Product where name = 'Product 2'", Product.class)
                        .getSingleResult();

                System.out.println("Fetched product 2: " + product2.getId());
                System.out.println(product2);
            });

            System.out.println("Finished");
        }
    }
}
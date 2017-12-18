package com.kevinsimard.hibernate.orm;

import com.kevinsimard.hibernate.orm.domain.entity.User;
import com.kevinsimard.hibernate.orm.domain.entity.User_;
import com.kevinsimard.hibernate.orm.util.DatabaseUtil;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class Example {

    public static void main(String[] args) {
        String username = "foobar";
        String email = "foo@bar.com";

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        long start = System.currentTimeMillis();

        DatabaseUtil.initialize();

        long diff = System.currentTimeMillis() - start;

        System.out.println("Hibernate Initialize: " + diff + " milliseconds");

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        start = System.currentTimeMillis();

        EntityManager em = DatabaseUtil.entityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.where(builder.equal(root.get(User_.username), username))
            .where(builder.equal(root.get(User_.email), email));

        @SuppressWarnings("unused")
        User user1 = em.createQuery(query).getSingleResult();

        diff = System.currentTimeMillis() - start;

        System.out.println("EntityManager Query: " + diff + " milliseconds");

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        start = System.currentTimeMillis();

        SelectSelectStep select = DatabaseUtil.dslContext().select(
            DSL.field("id", Long.class),
            DSL.field("username", String.class),
            DSL.field("email", String.class)
        );

        @SuppressWarnings("unused")
        User user2 = (User) select.from("user")
            .where("username = ?", username)
            .and("email = ?", email)
            .fetchAnyInto(User.class);

        diff = System.currentTimeMillis() - start;

        System.out.println("JOOQ Query: " + diff + " milliseconds");
    }
}

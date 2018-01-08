package com.kevinsimard.hibernate.orm;

import com.kevinsimard.hibernate.orm.domain.entity.User;
import com.kevinsimard.hibernate.orm.util.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class Example {

    private static final Logger log = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) {
        String username = "foobar";
        String email = "foo@bar.com";

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        long start = System.currentTimeMillis();

        DatabaseUtil.initialize();

        long diff = System.currentTimeMillis() - start;

        log.info(String.format("Hibernate Initialize: %d milliseconds", diff));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        start = System.currentTimeMillis();

        EntityManager em = DatabaseUtil.entityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.where(builder.equal(root.get("username"), username))
            .where(builder.equal(root.get("email"), email));

        em.createQuery(query).getSingleResult();

        diff = System.currentTimeMillis() - start;

        log.info(String.format("EntityManager Query: %d milliseconds", diff));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        start = System.currentTimeMillis();

        DatabaseUtil.dslContext()
            .select().from("user")
            .where("username = ?", username)
            .and("email = ?", email)
            .fetchAnyInto(User.class);

        diff = System.currentTimeMillis() - start;

        log.info(String.format("JOOQ Query: %d milliseconds", diff));
    }
}

package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE id = ANY " +
            "(select DISTINCT(us.id) FROM users us JOIN review_360 rv ON us.id=rv.user_id where rv.created_at> ?1 ) " +
            "OR id = ANY " +
            "(select DISTINCT(us.id) FROM users us JOIN apartments ap ON us.id=ap.user_id JOIN review_360 rv ON ap.id=rv.apartment_id where rv.created_at> ?1 )",nativeQuery = true)
    List<User> getByDate(String code);
}

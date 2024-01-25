package com.crabcodingtest.domain.signup.repository;

import com.crabcodingtest.domain.signup.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SignupUserRepository_Test {

    @PersistenceContext
    private EntityManager em; // 스프링이 EntityManager Bean을 주입시켜줌.

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class) // JPQL : "select user from SignupUser user"
                .getResultList();
    }

    public List<User> findByName(String name){
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

}

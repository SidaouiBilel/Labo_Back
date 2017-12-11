package com.login.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.login.model.User;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findAll() {
        return entityManager.createQuery("Select u from User u where u.removed = false", User.class).getResultList();
    }

    public User findByEmail(String email) {
        if (email == null) {
            return null;
        } else {
            email = email.trim().toLowerCase();
        }
        try {
            return entityManager.createQuery("Select u from User u where u.email = ?1", User.class).setParameter(1, email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public User merge(User user) {
        return entityManager.merge(user);
    }
}

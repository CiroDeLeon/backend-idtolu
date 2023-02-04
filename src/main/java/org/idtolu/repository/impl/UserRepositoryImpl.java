package org.idtolu.repository.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.idtolu.entity.User;
import org.idtolu.repository.UserRepository;



@Singleton
public class UserRepositoryImpl implements UserRepository {

    @Inject
    private EntityManager em;

    @Override
    public User findByUsername(String username) {
    	try {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                 .setParameter("username", username)
                 .getSingleResult();
    	}catch(Exception e) {
    		return null;
    	}
    }

    @Override
    @Transactional
    public void persist(User user) {
        em.persist(user);
    }

    

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
	}

	@Override
	@Transactional
	public void update(User user) {
	    em.merge(user);
	}


}


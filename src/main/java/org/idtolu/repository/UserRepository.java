package org.idtolu.repository;

import java.util.List;

import javax.inject.Singleton;

import org.idtolu.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public interface UserRepository extends PanacheRepository<User> {

    User findByUsername(String username);
    List<User> findAllUsers();
	void update(User user);
}

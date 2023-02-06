package org.idtolu.service;



import javax.inject.Inject;
import javax.inject.Singleton;

import org.idtolu.dto.Credentials;
import org.idtolu.entity.User;
import org.idtolu.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;




@Singleton
public class LoginService {

    @Inject
    UserRepository userRepository;

    public User login(Credentials credentials) throws Exception {
       User user = userRepository.findByUsername(credentials.getUsername());
        if (user == null) {
            throw new Exception("Invalid username or password");
        }
        if (!BCrypt.checkpw(credentials.getPassword(), user.getPassword())) {
            throw new Exception("Invalid username or password");
        }
        return user;
    }
    public User signup(Credentials credentials) throws Exception {
        if (userExists(credentials.getUsername())) {
            throw new Exception("Username already exists");
        }

        User user = new User();
        user.setUsername(credentials.getUsername());
        user.setPassword(hashPassword(credentials.getPassword()));
        user.setRole(credentials.getRole());
        // Save user to database
        this.userRepository.persist(user);

        return user;
    }
    private String hashPassword(String password) {
    	return BCrypt.hashpw(password, BCrypt.gensalt());
    }
	public boolean userExists(String username) {
		User u=this.userRepository.findByUsername(username);
		if(u!=null) {
			return true;
		}
		return false;
		/*
		Iterator<User> it=this.userRepository.findAllUsers().iterator();
		while(it.hasNext()) {
			User u=it.next();
			if(u.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
		*/
	}
	public User resetPassword(Credentials obj) throws Exception {
	    User user = userRepository.findByUsername(obj.getUsername());
	    if (user == null) {
	        throw new Exception("Username not found");
	    }
	    user.setPassword(hashPassword(obj.getPassword()));
	    userRepository.update(user);
	    return user;
	}
	public String getRoleByUsername(String userName) throws Exception{
		User user = userRepository.findByUsername(userName);
		if (user == null) {
	        throw new Exception("Username not found");
	    }
		return user.getRole();
	}

}


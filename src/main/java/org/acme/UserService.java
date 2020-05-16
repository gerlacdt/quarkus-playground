package org.acme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserRepository userRepository;

    @Transactional
    public User save(User u) {
        userRepository.persist(u);
        return u;
    }

    @Transactional
    public List<User> findAll() {
        var result = userRepository.findAll().list();
        log.info("UserService:findAll() result: {}", result);
        return result;
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return userRepository.deleteById(id);
    }
}

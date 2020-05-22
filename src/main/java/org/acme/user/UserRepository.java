package org.acme.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  void persist(User user);

  User update(User u);

  List<User> findAll();

  Optional<User> findById(Long id);

  boolean deleteById(Long id);

  long deleteAll();
}

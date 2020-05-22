package org.acme.user;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PanacheUserRepository implements PanacheRepository<User> {}

package org.acme;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Version
    public long version;

    public String username;
    public String email;
    public short age;

    @Column(name = "is_premium")
    public boolean premium;

    @CreationTimestamp
    @Column(name = "created_at")
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;
}

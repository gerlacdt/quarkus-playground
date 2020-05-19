package org.acme;

import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Version public long version;

  @NotBlank(message = "Username may not be blank")
  public String username;

  @NotBlank(message = "Email may not be blank")
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

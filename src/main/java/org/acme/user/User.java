package org.acme.user;

import java.time.Instant;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
@Data
public class User {

  @Nullable
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Nullable @Version public long version;

  @Nullable
  @NotBlank(message = "Username may not be blank")
  public String username;

  @Nullable
  @NotBlank(message = "Email may not be blank")
  public String email;

  @Nullable public short age;

  @Nullable
  @Column(name = "is_premium")
  public boolean premium;

  @Nullable
  @CreationTimestamp
  @Column(name = "created_at")
  public Instant createdAt;

  @Nullable
  @UpdateTimestamp
  @Column(name = "updated_at")
  public Instant updatedAt;
}

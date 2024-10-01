package br.com.g10.BEM.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

// @Data - adiciona os métodos equals, hashCode, toString, getters e setters
// @Getter - adiciona os métodos getters em todos os atributos
// @Setter - adiciona os métodos setters em todos os atributos
@Data
@Entity
@Table(name = "users")
public class UserModel {    
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column()
    private String name;

    @Column()
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

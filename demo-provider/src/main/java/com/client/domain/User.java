package com.client.domain;



import com.common.model.BaseModel;

import javax.persistence.*;

/*jpa 使用实例  乐观锁的使用  @OptimisticLocking*/
@Entity
@Table(name = "user")
public class User extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = true, length = 50)
    private String username;

    private String password;

    @Version
    private Integer version;
    @PrePersist
    public void prePersist() {
        version = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="USERS")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<ShopEntity> shops = new HashSet<>();

    public UserEntity() {
    }

    public void addRole(RoleEntity role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void addShop(ShopEntity shop){
        shops.add(shop);
        shop.getUsers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public Set<ShopEntity> getShops() {
        return shops;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void setShops(Set<ShopEntity> shops) {
        this.shops = shops;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
//                ", shops=" + shops +
                '}';
    }
}
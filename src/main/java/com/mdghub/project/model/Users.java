package com.mdghub.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userName"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone")
        }
)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @NotBlank
    @Size(min = 5, max = 50)
    private String userName;

    @NotBlank
    private String password;
    @NotBlank
    @Size(min = 5, max = 50)
    private String firstName;
    @Size(min = 5, max = 50)
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 10, max = 12)
    private String phone;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "roleId")
    )
    private Set<Roles> roles;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private Set<Product> product;
    public Users() {
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "addressId")
    )
    private List<Address> address = new ArrayList<>();


    public Users( String phone, String email,
                 String firstName,String lastName, String password, String userName) {
        //this.address = address;
        this.phone = phone;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Set<Product> getProduct() {
        return product;
    }

    public void setProduct(Set<Product> product) {
        this.product = product;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}

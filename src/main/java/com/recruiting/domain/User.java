package com.recruiting.domain;

import com.recruiting.converter.CryptoConverter;
import com.recruiting.utils.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "user")
//@Where( clause = "active = true" )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends AbstractEntity implements UserDetails {

    // region Instance Fields
    @Column(name = "username", unique = true)
    @Email
    private String username;


//    @ColumnTransformer(
//            read = "decrypt( 'AES', '00', pswd  )",
//            write = "encrypt('AES', '00', ?)"
//    )
    @Convert(converter = CryptoConverter.class)
    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "non_expired")
    private Boolean accountNonExpired;

    @Column(name = "non_locked")
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "approved")
    private Boolean approved;

    @Column(insertable = false, updatable = false)
    private String dtype;

//    @Column(name = "user_type")
//    @Enumerated(EnumType.STRING)
//    private UserType type;

    @ManyToMany
    private List<Authority> grantedAuthorities;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Address address;
    // endregion

    // region Constructors
    public User() {
    }

    public User(String username,
            String password,
            Boolean enabled,
            List<Authority> grantedAuthorities, Address address) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.grantedAuthorities = grantedAuthorities;
        this.address = address;
        setAccountNonExpired(true);
        setAccountNonLocked(true);
        setCredentialsNonExpired(true);
    }

    // endregion

    // region Transient methods
    public void correctStrings() {
        this.address.correctStrings();
        this.username = StringUtils.correctWhiteSpaces(username);
    }

    public void setRole(Authority grantedAuthority) {
        if (grantedAuthorities == null) grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(grantedAuthority);
    }
    // endregion

    // region Getters and Setters
    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(List<Authority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "User [" +
                "id: " + id + "\t" +
                "username: " + username +
                "]";
    }
    // endregion
}

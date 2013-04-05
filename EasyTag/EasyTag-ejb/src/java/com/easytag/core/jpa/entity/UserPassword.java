package com.easytag.core.jpa.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity-class for user authentification data
 * @author Vildanov Ruslan
 */
@Entity
@Table(name="user_passwords")
@NamedQueries({
    @NamedQuery(name = "UserPassword.findAll", query = "SELECT up FROM UserPassword up"),
    @NamedQuery(name = "UserPassword.findByPassword", query = "SELECT up FROM UserPassword up WHERE up.password = :password"),
    @NamedQuery(name = "UserPassword.findByUser", query = "SELECT up FROM UserPassword up WHERE up.user = :user"),
    @NamedQuery(name = "UserPassword.findUserByPassword", query="SELECT us FROM UserPassword up INNER JOIN up.user us WHERE up.password = :password"),
    @NamedQuery(name = "UserPassword.findByLogin", query = "SELECT u FROM UserPassword u WHERE u.login = :login"),
    @NamedQuery(name = "UserPassword.findByLoginAndPassword", query="SELECT u FROM UserPassword u WHERE u.login = :login AND u.password = :password")
})
public class UserPassword implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
       
    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne
    private User user;
    
    @Column(name="password", nullable=false)
    private String password;
    
    @Column(name="status")
    private int status;
    
    @Column(name="login")
    private String login;
    
    @Column(name="extra_info")
    private String extraInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserPassword other = (UserPassword) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserPassword" + "id=" + id  + ", user=" + user + ", password=" + password + ", status=" + status + ", extraInfo=" + extraInfo + '}';
    }
    
}

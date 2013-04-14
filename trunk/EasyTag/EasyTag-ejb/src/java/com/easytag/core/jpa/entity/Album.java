package com.easytag.core.jpa.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity-class for user's album
 * @author Vildanov Ruslan
 */
@Entity
@Table(name = "albums")
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a"),
    @NamedQuery(name = "Album.findByAlbumId", query = "SELECT a FROM Album a WHERE a.album_id = :album_id"),
    @NamedQuery(name = "Album.findByAlbumName", query = "SELECT a FROM Album a WHERE a.name = :albumName")    
})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long album_id;
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "additional_info")
    private String additionalInfo;
    
    @JoinColumn(name = "parent_id")
    @ManyToOne    
    private Album parentAlbum;
    
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    
    @Column(name = "number_of_pictures")
    private int number;
    
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    
    @Column(name = "status")
    private int status;
   
    @Column(name = "extra_info")
    private String extraInfo;

    public Long getId() {
        return album_id;
    }

    public void setId(Long id) {
        this.album_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Album getParentAlbum() {
        return parentAlbum;
    }

    public void setParentAlbum(Album parentAlbum) {
        this.parentAlbum = parentAlbum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.album_id);
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
        final Album other = (Album) obj;
        if (!Objects.equals(this.album_id, other.album_id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Album{" + "album_id=" + album_id + ", name=" + name + ", additionalInfo=" + additionalInfo + ", parentAlbum=" + parentAlbum + ", user=" + user + ", number=" + number + ", dateCreation=" + dateCreation + ", status=" + status + ", extraInfo=" + extraInfo + '}';
    }

   
}

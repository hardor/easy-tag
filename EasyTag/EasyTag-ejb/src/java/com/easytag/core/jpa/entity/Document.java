package com.easytag.core.jpa.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity-class for document
 * @author Vildanov Ruslan
 */
@Entity @Table(name = "documents")
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;    
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "information")
    private String information;
    
    @Column(name = "rating")
    private int rating;  
    
    @Column(name = "length_",nullable=false)
    private Long length;
    
    @Column(name = "width",nullable=false)
    private Long width;
    
    @Column(name="status")
    private int status;
    
    @Column(name="extra_info")
    private String extraInfo;

    @JoinColumn(name="user_id", nullable=false) @ManyToOne
    private User user;
    
    @JoinColumn(name="album_id", nullable=false) @ManyToOne
    private Album album;
    
    @JoinColumn(name = "tag_id")
    @ManyToMany
    private Tag tag;
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
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
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Document other = (Document) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", name=" + name + ", information=" + information + ", rating=" + rating + ", length=" + length + ", width=" + width + ", status=" + status + ", extraInfo=" + extraInfo + ", user=" + user + ", album=" + album + ", tag=" + tag + '}';
    }

   
}

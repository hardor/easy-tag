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
import javax.persistence.Table;

/**
 * Entity-class for group of fragments
 * @author Vildanov Ruslan
 */
@Entity 
@Table(name = "fragment_groups")
public class FragmentGroup implements Serializable {

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
            
    @JoinColumn(name = "album")
    @ManyToOne
    private Album album;
    
    @Column(name = "number_of_fragments")
    private int numberFragments;
        
    @Column(name = "status")
    private int status;
    
    @Column(name = "extra_info")
    private String extraInfo;

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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getNumberFragments() {
        return numberFragments;
    }

    public void setNumberFragments(int numberFragments) {
        this.numberFragments = numberFragments;
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
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final FragmentGroup other = (FragmentGroup) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "FragmentGroup{" + "id=" + id + ", name=" + name + ", information=" + information + ", rating=" + rating + ", album=" + album + ", numberFragments=" + numberFragments + ", status=" + status + ", extraInfo=" + extraInfo + '}';
    }    
    
}

package com.easytag.core.jpa.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity-class for document's fragment
 * @author Vildanov Ruslan
 */
@Entity
@Table(name = "fragments")
@NamedQueries({
    @NamedQuery(name = "Fragment.deletByDocument", query = "DELETE FROM Fragment f WHERE f.document.id = :doc_id")
})
public class Fragment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id; 
       
    @Column(name = "name",nullable=false)
    private String name;
        
    @Column(name = "first_coordinate_x",nullable=false)
    private Long firstCoordinateX;
    
    @Column(name = "first_coordinate_y",nullable=false)
    private Long firstCoordinateY;
    
    @Column(name = "second_coordinate_x",nullable=false)
    private Long secondCoordinateX;
    
    @Column(name = "second_coordinate_y",nullable=false)
    private Long secondCoordinateY;
    
    @Column(name = "information")
    private String information;
        
    @Column(name = "rating")
    private int rating;
            
    @JoinColumn(name = "tag_id")
    @ManyToMany
    private Set<Tag> tags;
    
    @JoinColumn(name = "fragment_group_id")
    @ManyToOne
    private FragmentGroup fragmentGroup;
    
    @JoinColumn(name = "document_id")
    @ManyToOne
    private Document document;
       
    @JoinColumn(name = "album_id")
    @ManyToOne
    private Album album;
    
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

    public Long getFirstCoordinateX() {
        return firstCoordinateX;
    }

    public void setFirstCoordinateX(Long firstCoordinateX) {
        this.firstCoordinateX = firstCoordinateX;
    }

    public Long getFirstCoordinateY() {
        return firstCoordinateY;
    }

    public void setFirstCoordinateY(Long firstCoordinateY) {
        this.firstCoordinateY = firstCoordinateY;
    }

    public Long getSecondCoordinateX() {
        return secondCoordinateX;
    }

    public void setSecondCoordinateX(Long secondCoordinateX) {
        this.secondCoordinateX = secondCoordinateX;
    }

    public Long getSecondCoordinateY() {
        return secondCoordinateY;
    }

    public void setSecondCoordinateY(Long secondCoordinateY) {
        this.secondCoordinateY = secondCoordinateY;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public FragmentGroup getFragmentGroup() {
        return fragmentGroup;
    }

    public void setFragmentGroup(FragmentGroup fragmentGroup) {
        this.fragmentGroup = fragmentGroup;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Fragment other = (Fragment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fragment{" + "id=" + id + ", name=" + name + ", firstCoordinateX=" + firstCoordinateX + ", firstCoordinateY=" + firstCoordinateY + ", secondCoordinateX=" + secondCoordinateX + ", secondCoordinateY=" + secondCoordinateY + ", information=" + information + ", rating=" + rating + ", tags=" + tags + ", fragmentGroup=" + fragmentGroup + ", album=" + album + ", status=" + status + ", extraInfo=" + extraInfo + '}';
    }
    
    
}

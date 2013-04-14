package com.easytag.core.jpa.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity-class for file
 *
 * @author Vildanov Ruslan
 */
@Entity
@Table(name = "document")
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByAlbum", query = "SELECT d FROM Document d where d.album.album_id=:album_id"),
    @NamedQuery(name = "Document.findByUrl", query = "SELECT d FROM Document d where d.url=:url"),
    @NamedQuery(name = "Document.findByExtraInfo", query = "SELECT d FROM Document d where d.extraInfo=:extraInfo")
})
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "name")
    private String name;
    //size of document
    @Column(name = "size_kb")
    private Long size;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "information")
    private String information;
    @Column(name = "rating")
    private int rating;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Column(name = "length_")
    private Long length;
    @Column(name = "width")
    private Long width;
    @Column(name = "status")
    private int status;
    @Column(name = "extra_info")
    private String extraInfo;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User createdBy;
    @JoinColumn(name = "album_id")
    @ManyToOne
    private Album album;
    @JoinColumn(name = "tag_id")
    @ManyToMany
    private Set<Tag> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Files{" + "id=" + id + ", url=" + url + ", name=" + name + ", size=" + size + ", information=" + information + ", rating=" + rating + ", dateCreation=" + dateCreation + ", status=" + status + ", extraInfo=" + extraInfo + ", createdBy=" + createdBy + ", album=" + album + '}';
    } 

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
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
}

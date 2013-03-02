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
import javax.persistence.Table;

/**
 * Entity-class for tag for document or fragment 
 * @author Vildanov Ruslan
 */
@Entity @Table(name = "tags")
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;    
    
    @Column(name="name",nullable=false,unique=true)
    private String name;
    
    @Column(name="status")
    private int status;
    
    @Column(name="extra_info")
    private String extraInfo;

    @JoinColumn(name = "fragment_id")
    @ManyToMany
    private Fragment fragment;
     
    @JoinColumn(name = "document_id")
    @ManyToMany
    private Document document;
        
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

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Tag other = (Tag) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", name=" + name + ", status=" + status + ", extraInfo=" + extraInfo + ", fragment=" + fragment + ", document=" + document + '}';
    }
 
   
}

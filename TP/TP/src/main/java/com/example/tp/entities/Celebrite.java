package com.example.tp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLInsert;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
//@Table(name="celebrite",uniqueConstraints=@UniqueConstraint(columnNames={"nom","prenom","nationalite","epoque"}))

//@SQLInsert(sql = "INSERT INTO Celebrite(num_celebrite, epoque, image, nationalite, nom, prenom, url, parent_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)" )
//@XmlRootElement
public class Celebrite implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Celebrity Number")
    private long numCelebrite;

    @NotBlank(message = "Family is mandatory")
    @Column(length = 16)
    @JsonProperty("Family")
    private String nom = "undefined";

    @NotBlank(message = "Name is mandatory")
    @Column(length = 16)
    @JsonProperty("Name")
    private String prenom = "undefined";

    @Column(length = 30)
    @JsonProperty("Nationality")
    private String nationalite;

    @Column(length = 4)
    @JsonProperty("Year of Birth")
    private String epoque;

    @Column(length = 255)
    @JsonProperty("URL")
    private String url;

    @Column(length = 255)
    @JsonProperty("Parent URL")
    private String parent_url;



    @Column(length = 255)
    @JsonProperty("Image")
    private String image;


    @ElementCollection(fetch = FetchType.EAGER)
    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name= "associeA",
            joinColumns= @JoinColumn(name="numCelebrite", referencedColumnName="numCelebrite"),
            inverseJoinColumns= @JoinColumn(name="codeM", referencedColumnName="code_m"))
    @JsonProperty("List of Monuments")
    private  Set<Monument> monuments = new HashSet<Monument>();





    public Celebrite() {
        super();

    }

    public Celebrite(long numCelebrite) {
        this();
        this.setNumCelebrite(numCelebrite);
    }




    public Celebrite(long numCelebrite, String nom, String prenom, String nationalite, String epoque) {
        this(numCelebrite);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setNationalite(nationalite);
        this.setEpoque(epoque);
        this.setUrl(url);
        this.setParent_url(parent_url);
        this.setImage(image);
    }



    public long getNumCelebrite() {
        return this.numCelebrite;
    }

    public void setNumCelebrite(long numCelebrite) {
        this.numCelebrite = numCelebrite;
        this.setUrl(url);
        this.setParent_url(parent_url);
        this.setImage(image);
    }

    public String getNom() {
        return nom;
    }



    public String setNom(String nom) {
        this.nom = nom;
        return nom;
    }



    public String getPrenom() {
        return prenom;
    }



    public String setPrenom(String prenom) {
        this.prenom = prenom;
        return prenom;
    }



    public String getNationalite() {
        return nationalite;
    }



    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }



    public String getEpoque() {
        return epoque;
    }



    public void setEpoque(String epoque) {
        this.epoque = epoque;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParent_url() {
        return parent_url;
    }

    public void setParent_url(String parent_url) {
        this.parent_url = parent_url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }





    @Override
    public String toString() {
        return "ID: " + this.numCelebrite +", Nom Celebrite: " + this.nom + ", Prénom Celebrite: " + this.prenom + ", Nationalite: " + this.nationalite +
                ", Epoque: " + this.epoque ;
    }

    /**
     * @return the monuments
     */
    public Set<Monument> getMonuments() {
        return monuments;
    }

    /**
     * @param monuments the monuments to set
     */
    public void setMonuments(Set<Monument> monuments) {
        this.monuments = monuments;
    }






}

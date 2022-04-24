package com.example.tp.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Entity
@Table(name = "monument")
public class Monument implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="code_m", length = 12)
    @JsonProperty("Monument Code")
    private String codeM;

    @Column(name="nom_m", length = 47)
    @JsonProperty("Monument Name")
    private String nomM;

    @Column(length = 10)
    @JsonProperty("Owner")
    private String proprietaire;

    @Column(name="typeMonument", length = 17)
    @JsonProperty("Type of Monument")
    private String typeMonument;

    @Column(name = "longitude")
    @JsonProperty("Longitude")
    private double longitude;

    @Column(name = "latitude")
    @JsonProperty("Latitude")
    private double latitude;

    @ManyToOne(targetEntity = Lieu.class)
    @JoinColumn(name="codeLieu")
    @JsonProperty("Lieu")
    private Lieu lieu;




//	@ManyToMany
//	@ManyToMany(mappedBy = "monuments", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(
//	    joinColumns = @JoinColumn( name = "codeM" ),
//	    inverseJoinColumns = @JoinColumn( name = "numCelebrite" ))


    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(joinColumns=@JoinColumn(name="code_m"), inverseJoinColumns=@JoinColumn(name="numCelebrite"))
    @JsonProperty("List of Celebrities")
    private Set<Celebrite> celebrities;



    @Column(length = 255)
    @JsonProperty("URL")
    private String url;

    @Column(length = 255)
    @JsonProperty("Parent URL")
    private String parent_url;



    public Monument() {
        super();
    }

    public Monument(String codeM) {
        this();
        this.setCodeM(codeM);
    }

    public Monument(String codeM, String nomM, String proprietaire, String typeMonument, double longitude,
                    double latitude, Lieu lieu) {
        this(codeM);
        this.setNomM(nomM);
        this.setProprietaire(proprietaire);
        this.setTypeMonument(typeMonument);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setLieu(lieu);
    }

    public String getCodeM() {
        return codeM;
    }

    public void setCodeM(String codeM) {
        this.codeM = codeM;

    }


    public String getNomM() {
        return nomM;
    }

    public void setNomM(String nomM) {
        this.nomM = nomM;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public String getTypeMonument() {
        return typeMonument;
    }

    public void setTypeMonument(String typeMonument) {
        this.typeMonument = typeMonument;
    }


    public double getLongitude() {
        return longitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public Lieu setLieu(Lieu lieu) {
        if(lieu != null) this.lieu = lieu;
        else this.lieu = new Lieu();
        return lieu;

    }



    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }


    public String getParent_url() {
        return parent_url;
    }


    /**
     * @param parent_url the parent_url to set
     */
    public void setParent_url(String parent_url) {
        this.parent_url = parent_url;
    }

    @Override
    public String toString() {
        return "Code Monument: " + this.codeM + ", Nom Monument: " + this.nomM + ", Proprietaire: " + this.proprietaire +
                ", Type Monument: " + this.typeMonument + ", Longitude: " + this.longitude + ", Latitude: " + this.latitude	+
                ", Localisation: " + this.lieu.getCodeInsee();
    }

    public Set<Celebrite> getCelebrities() {
        return celebrities;
    }

    public void setCelebrities(Set<Celebrite> celebrities) {
        this.celebrities = celebrities;
    }

}


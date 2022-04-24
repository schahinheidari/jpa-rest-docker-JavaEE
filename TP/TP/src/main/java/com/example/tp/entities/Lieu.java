package com.example.tp.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "lieu")
public class Lieu implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 5)
    @JsonProperty("Code Insee")
    private String codeInsee;

//    @MapsId
//    @PrimaryKeyJoinColumn
//    @OneToOne(mappedBy = "lieu", cascade = CascadeType.ALL)
//    @JoinColumn(name="dep", insertable=false ,updatable=false)


    @JsonBackReference
    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = Departement.class)
    @JoinColumn(name="dep")		//, insertable=false ,updatable=false)
    @JsonProperty("Departement")
    private Departement departement;

    @Column(length = 30)
    @JsonProperty("Commune Name")
    private String nomCom;

    @JsonProperty("Longitude")
    private double longitude;

    @JsonProperty("Latitude")
    private double latitude;

    @JsonProperty("URL")
    @Column(length = 255)
    private String url;

    @JsonProperty("Lieu URL")
    @Column(length = 255)
    private String parent_url;

    @JsonProperty("Departement URL")
    @Column(length = 255)
    private String departement_url;



    // @OneToMany(mappedBy = "lieu",cascade = CascadeType.ALL)
    // @JsonProperty("Set of Monuments")
    // private Set<Monument> monuments;



    public Lieu() {
        super();
    }

    public Lieu(String codeInsee) {
        this();
        this.setCodeInsee(codeInsee);
    }




    public Lieu(String codeInsee, String nomCom, double longitude, double latitude, Departement departement) {
        this(codeInsee);
        this.setNomCom(nomCom);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setDepartement(departement);
    }

    public String getCodeInsee() {
        return this.codeInsee;
    }

    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;

    }

    public String getNomCom() {
        return nomCom;
    }


    public void setNomCom(String nomCom) {
        this.nomCom = nomCom;
    }


    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // @JsonGetter
    public Departement getDepartement() {
        return departement;
    }

    //	@JsonSetter
    public Departement setDepartement(Departement departement) {
        if(departement != null) this.departement = departement;
        else this.departement = new Departement();
        return departement;
    }

    public String getUrl() {
        return url;
    }

    public String getParent_url() {
        return parent_url;
    }

    @Override
    public String toString() {
        return "Code Insee=" + this.codeInsee + ", Nom Commune: " + this.nomCom + ", Departement: "
                + this.departement.getNumDep() +  ", Longitude: " + this.longitude + ", Latitude: " + this.latitude ;
    }

    /**
     * @return the departement_url
     */
    public String getDepartement_url() {
        return departement_url;
    }

    /**
     * @param departement_url the departement_url to set
     */
    public void setDepartement_url(String departement_url) {
        this.departement_url = departement_url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param parent_url the parent_url to set
     */
    public void setParent_url(String parent_url) {
        this.parent_url = parent_url;
    }



}

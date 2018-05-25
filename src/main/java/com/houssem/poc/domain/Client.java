package com.houssem.poc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.houssem.poc.domain.enumeration.ClientType;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private ClientType type;

    @NotNull
    @Column(name = "cin", nullable = false)
    private String cin;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Compte> comptes = new HashSet<>();

    @ManyToOne
    private Compte compte;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Client nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Client prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public ClientType getType() {
        return type;
    }

    public Client type(ClientType type) {
        this.type = type;
        return this;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public String getCin() {
        return cin;
    }

    public Client cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Client dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public Client telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Compte> getComptes() {
        return comptes;
    }

    public Client comptes(Set<Compte> comptes) {
        this.comptes = comptes;
        return this;
    }

    public Client addComptes(Compte compte) {
        this.comptes.add(compte);
        compte.setClient(this);
        return this;
    }

    public Client removeComptes(Compte compte) {
        this.comptes.remove(compte);
        compte.setClient(null);
        return this;
    }

    public void setComptes(Set<Compte> comptes) {
        this.comptes = comptes;
    }

    public Compte getCompte() {
        return compte;
    }

    public Client compte(Compte compte) {
        this.compte = compte;
        return this;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if (client.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", type='" + getType() + "'" +
            ", cin='" + getCin() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}

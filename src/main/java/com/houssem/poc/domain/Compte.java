package com.houssem.poc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.houssem.poc.domain.enumeration.CompteType;

/**
 * A Compte.
 */
@Entity
@Table(name = "compte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "date_ouv")
    private Instant dateOuv;

    @Column(name = "adresse")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private CompteType type;

    @Column(name = "actif")
    private Boolean actif;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "compte")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Compte reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getDateOuv() {
        return dateOuv;
    }

    public Compte dateOuv(Instant dateOuv) {
        this.dateOuv = dateOuv;
        return this;
    }

    public void setDateOuv(Instant dateOuv) {
        this.dateOuv = dateOuv;
    }

    public String getAdresse() {
        return adresse;
    }

    public Compte adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public CompteType getType() {
        return type;
    }

    public Compte type(CompteType type) {
        this.type = type;
        return this;
    }

    public void setType(CompteType type) {
        this.type = type;
    }

    public Boolean isActif() {
        return actif;
    }

    public Compte actif(Boolean actif) {
        this.actif = actif;
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Client getClient() {
        return client;
    }

    public Compte client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Compte clients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    public Compte addClient(Client client) {
        this.clients.add(client);
        client.setCompte(this);
        return this;
    }

    public Compte removeClient(Client client) {
        this.clients.remove(client);
        client.setCompte(null);
        return this;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
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
        Compte compte = (Compte) o;
        if (compte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", dateOuv='" + getDateOuv() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", type='" + getType() + "'" +
            ", actif='" + isActif() + "'" +
            "}";
    }
}

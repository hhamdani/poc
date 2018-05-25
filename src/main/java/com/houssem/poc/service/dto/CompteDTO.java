package com.houssem.poc.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.houssem.poc.domain.enumeration.CompteType;

/**
 * A DTO for the Compte entity.
 */
public class CompteDTO implements Serializable {

    private Long id;

    @NotNull
    private String reference;

    private Instant dateOuv;

    private String adresse;

    private CompteType type;

    private Boolean actif;

    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getDateOuv() {
        return dateOuv;
    }

    public void setDateOuv(Instant dateOuv) {
        this.dateOuv = dateOuv;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public CompteType getType() {
        return type;
    }

    public void setType(CompteType type) {
        this.type = type;
    }

    public Boolean isActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompteDTO compteDTO = (CompteDTO) o;
        if(compteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompteDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", dateOuv='" + getDateOuv() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", type='" + getType() + "'" +
            ", actif='" + isActif() + "'" +
            "}";
    }
}

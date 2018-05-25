package com.houssem.poc.service.mapper;

import com.houssem.poc.domain.*;
import com.houssem.poc.service.dto.CompteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Compte and its DTO CompteDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface CompteMapper extends EntityMapper<CompteDTO, Compte> {

    @Mapping(source = "client.id", target = "clientId")
    CompteDTO toDto(Compte compte);

    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "clients", ignore = true)
    Compte toEntity(CompteDTO compteDTO);

    default Compte fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compte compte = new Compte();
        compte.setId(id);
        return compte;
    }
}

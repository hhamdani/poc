package com.houssem.poc.service.mapper;

import com.houssem.poc.domain.*;
import com.houssem.poc.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {CompteMapper.class})
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {

    @Mapping(source = "compte.id", target = "compteId")
    ClientDTO toDto(Client client);

    @Mapping(target = "comptes", ignore = true)
    @Mapping(source = "compteId", target = "compte")
    Client toEntity(ClientDTO clientDTO);

    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}

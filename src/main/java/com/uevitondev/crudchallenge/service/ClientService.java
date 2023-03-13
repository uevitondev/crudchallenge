package com.uevitondev.crudchallenge.service;

import com.uevitondev.crudchallenge.dto.ClientDTO;
import com.uevitondev.crudchallenge.entity.Client;
import com.uevitondev.crudchallenge.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllClientPaged(PageRequest pageRequest) {
        return clientRepository.findAll(pageRequest).map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Entity not found! id: " + id));
        return new ClientDTO(client);
    }


}
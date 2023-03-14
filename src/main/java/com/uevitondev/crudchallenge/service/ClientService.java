package com.uevitondev.crudchallenge.service;

import com.uevitondev.crudchallenge.dto.ClientDTO;
import com.uevitondev.crudchallenge.entity.Client;
import com.uevitondev.crudchallenge.repository.ClientRepository;
import com.uevitondev.crudchallenge.service.exceptions.DatabaseException;
import com.uevitondev.crudchallenge.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<ClientDTO> findAllClientPaged(PageRequest pageRequest) {
        return clientRepository.findAll(pageRequest).map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findClientById(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found! id: " + id));

        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO insertClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
        client = clientRepository.save(client);

        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        try {
            Client client = clientRepository.getReferenceById(id);
            client.setName(clientDTO.getName());
            client.setCpf(clientDTO.getCpf());
            client.setIncome(clientDTO.getIncome());
            client.setBirthDate(clientDTO.getBirthDate());
            client.setChildren(clientDTO.getChildren());
            client = clientRepository.save(client);

            return new ClientDTO(client);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Entity not found! id: " + id);
        }

    }

    public void deleteClient(Long id) {
        try {

            clientRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found! id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation!");
        }
    }

}

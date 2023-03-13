package com.uevitondev.crudchallenge.resource;

import com.uevitondev.crudchallenge.dto.ClientDTO;
import com.uevitondev.crudchallenge.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllClientPaged(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "5") Integer linesPerPage,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "orderBy", defaultValue = "name") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<ClientDTO> allClientPaged = clientService.findAllClientPaged(pageRequest);

        return ResponseEntity.ok().body(allClientPaged);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id) {
        return ResponseEntity.ok().body(clientService.findClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insertClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok().body(clientService.insertClient(clientDTO));
    }


}

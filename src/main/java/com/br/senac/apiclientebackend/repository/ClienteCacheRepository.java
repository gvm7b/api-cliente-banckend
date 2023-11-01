package com.br.senac.apiclientebackend.repository;

import com.br.senac.apiclientebackend.entitys.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteCacheRepository {

    @Autowired
    private ClienteRepository clienteRepository;

    @Cacheable("clientes")
    public List<Cliente> carregarTodosClientes(){
        return clienteRepository.findAll();
    }
}

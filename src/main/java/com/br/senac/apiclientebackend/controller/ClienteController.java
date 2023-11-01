package com.br.senac.apiclientebackend.controller;

import com.br.senac.apiclientebackend.entitys.Cliente;
import com.br.senac.apiclientebackend.entitys.Endereco;
import com.br.senac.apiclientebackend.mappers.ClienteMapper;
import com.br.senac.apiclientebackend.mappers.EnderecoMapper;
import com.br.senac.apiclientebackend.modelo.*;
import com.br.senac.apiclientebackend.repository.ClienteCacheRepository;
import com.br.senac.apiclientebackend.repository.ClienteRepository;
import com.br.senac.apiclientebackend.repository.EnderecoRepository;
import com.br.senac.apiclientebackend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    ClienteCacheRepository clienteCacheRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EmailService emailService;

    @GetMapping(path = "/carregar")
    public ResponseEntity<List<ClienteResponse>> carregarClientes(){
        List<Cliente> clientes = clienteCacheRepository.carregarTodosClientes();
        List<ClienteResponse> clienteList = clienteRepository.findAll().stream()
                .map(ClienteMapper::clienteResponseMapper)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clienteList);
    }

    @GetMapping(path = "/carregar_pagina")
    public ResponseEntity<DataResponse> carregarClientesPaginada(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Cliente> clienteList = clienteRepository.findAll(pageable);

        List<ClienteResponse> clienteResponseList = clienteList.getContent()
                .stream().map(ClienteMapper::clienteResponseMapper)
                .collect(Collectors.toList());

        InfoRow infoRow = new InfoRow();
        infoRow.setPage(clienteList.getNumber() + 1);
        infoRow.setPageCount(clienteList.getTotalPages());
        infoRow.setTotalElements(clienteList.getTotalElements());

        DataResponse out = new DataResponse();
        out.setInfoRows(infoRow);
        out.setRows(clienteResponseList);

        return ResponseEntity.ok(out);
    }


    @GetMapping(path = "/carregar/{id}")
    public ResponseEntity<ClienteResponse> carregarById(@PathVariable Long id){
        Cliente cliente = clienteRepository.findById(id).get();

        List<Endereco> enderecoList = enderecoRepository.carregarEnderecosByClienteId(id);

        List<EnderecoResponse> enderecoResponseList = new ArrayList<>();

        for(Endereco endereco : enderecoList){
            enderecoResponseList.add(EnderecoMapper.enderecoResponseMapper(endereco));
        }

        ClienteResponse out = this.clienteResponseMapper(cliente);
        out.setEnderecos(enderecoResponseList);

        return ResponseEntity.ok(out);
    }

    @PostMapping(path = "/cadastrar")
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody ClienteRequest clienteRequest){
        Cliente cliente = clienteRepository.save(ClienteMapper.clienteMapper(clienteRequest));

        cacheManager.getCache("clientes").clear();

        emailService.criarFilaEmail("Cliente criado com sucesso, <strong>id: " + cliente.getId(), "guilherme.machado5@alunos.sc.senac.br", "Criação de Cliente");


        return ResponseEntity.ok(ClienteMapper.clienteResponseMapper(cliente));
    }

    @PutMapping(path = "/atualizar/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest){
        Cliente cliente = clienteRepository.findById(id)
                .map(record -> {
                    record.setNome(clienteRequest.getNome());
                    record.setSobreNome(clienteRequest.getSobreNome());
                    record.setDataNascimento(clienteRequest.getDataNascimento());
                    record.setDocumento(clienteRequest.getDocumento());
                    record.setEmail(clienteRequest.getEmail());
                    record.setTelefone(clienteRequest.getTelefone());

                    return clienteRepository.save(record);
                }).get();

        ClienteResponse out = this.clienteResponseMapper(cliente);

        return ResponseEntity.ok(out);
    }

    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
        clienteRepository.deleteById(id);

        return ResponseEntity.ok(null);
    }


    public ClienteResponse clienteResponseMapper (Cliente cliente){
        ClienteResponse out = new ClienteResponse();
        out.setId(cliente.getId());
        out.setNome(cliente.getNome());
        out.setSobreNome(cliente.getSobreNome());
        out.setDataNascimento(cliente.getDataNascimento());
        out.setDocumento(cliente.getDocumento());
        out.setEmail(cliente.getEmail());
        out.setTelefone(cliente.getTelefone());

        return out;
    }
}

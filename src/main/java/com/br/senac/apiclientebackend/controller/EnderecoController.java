package com.br.senac.apiclientebackend.controller;

import com.br.senac.apiclientebackend.entitys.Cliente;
import com.br.senac.apiclientebackend.entitys.Endereco;
import com.br.senac.apiclientebackend.mappers.EnderecoMapper;
import com.br.senac.apiclientebackend.modelo.DataResponse;
import com.br.senac.apiclientebackend.modelo.EnderecoRequest;
import com.br.senac.apiclientebackend.modelo.EnderecoResponse;
import com.br.senac.apiclientebackend.modelo.InfoRow;
import com.br.senac.apiclientebackend.repository.ClienteRepository;
import com.br.senac.apiclientebackend.repository.EnderecoCacheRepository;
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
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    EnderecoCacheRepository enderecoCacheRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EmailService emailService;

    @GetMapping("/carregar")
    public ResponseEntity<List<EnderecoResponse>> carregarEnderecos(){
        List<Endereco> enderecos = enderecoCacheRepository.carregarTodosEnderecos();
        List<EnderecoResponse> out = enderecoRepository.findAll().stream()
                .map(EnderecoMapper::enderecoResponseMapper)
                .collect(Collectors.toList());

        return ResponseEntity.ok(out);
    }


    @GetMapping(path = "/carregar/{id}")
    public ResponseEntity<EnderecoResponse> carregarByIdEndereco(@PathVariable Long id) {
       Endereco endereco =  enderecoRepository.findById(id).get();

        EnderecoResponse out = EnderecoMapper.enderecoResponseMapper(endereco);

        return ResponseEntity.ok(out);
    }

    @GetMapping(path = "/carregar_pagina")
    public ResponseEntity<DataResponse> carregarEndPaginado(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size){
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Endereco> enderecoList = enderecoRepository.findAll(pageable);

        List<EnderecoResponse> enderecoResponseList = enderecoList.getContent()
                .stream().map(EnderecoMapper::enderecoResponseMapper)
                .collect(Collectors.toList());

        InfoRow infoRow = new InfoRow();
        infoRow.setPage(enderecoList.getNumber() + 1);
        infoRow.setPageCount(enderecoList.getTotalPages());
        infoRow.setTotalElements(enderecoList.getTotalElements());

        DataResponse out = new DataResponse();
        out.setInfoRows(infoRow);
        out.setRows(enderecoResponseList);

        return ResponseEntity.ok(out);
    }


    @PostMapping(path = "/cadastrar")
    public ResponseEntity<EnderecoResponse> cadastrarEndereco(@RequestBody EnderecoRequest enderecoRequest){
        Cliente cliente = clienteRepository.findById(enderecoRequest.getClienteId()).get();

        cacheManager.getCache("enderecos").clear();

        Endereco endereco = this.enderecoMapper(enderecoRequest, cliente);
        Endereco retorno = enderecoRepository.save(endereco);

        emailService.enviarEmailHtml("Endereço cadastrado com sucesso, <strong/>id: " +endereco.getId(), "guilherme.machado5@alunos.sc.senac.br", "Cadastro de Endereço");

        return ResponseEntity.ok(EnderecoMapper.enderecoResponseMapper(endereco));
    }

    @PutMapping(path = "/atualizar/{id}")
    public ResponseEntity<EnderecoResponse> atualizarEndereco(@PathVariable Long id, @RequestBody EnderecoRequest enderecoRequest){
        Cliente cliente = clienteRepository.findById(enderecoRequest.getClienteId()).get();

        Endereco endereco = enderecoRepository.findById(id).map(record -> {
            record.setRua(enderecoRequest.getRua());
            record.setBairro(enderecoRequest.getBairro());
            record.setCidade(enderecoRequest.getCidade());
            record.setEstado(enderecoRequest.getEstado());
            record.setNomeResponsavel(enderecoRequest.getNomeResponsavel());
            record.setCliente(cliente);

            return enderecoRepository.save(record);
        }).get();

        EnderecoResponse out = EnderecoMapper.enderecoResponseMapper(endereco);

        return ResponseEntity.ok(out);
    }

    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id){
        enderecoRepository.deleteById(id);

        return ResponseEntity.ok(null);
    }

    public Endereco enderecoMapper(EnderecoRequest enderecoRequest, Cliente cliente){
        Endereco out = new Endereco();
        out.setId(enderecoRequest.getId());
        out.setRua(enderecoRequest.getRua());
        out.setBairro(enderecoRequest.getBairro());
        out.setCidade(enderecoRequest.getCidade());
        out.setNomeResponsavel(enderecoRequest.getNomeResponsavel());
        out.setEstado(enderecoRequest.getEstado());
        out.setCliente(cliente);

        return out;
    }

}

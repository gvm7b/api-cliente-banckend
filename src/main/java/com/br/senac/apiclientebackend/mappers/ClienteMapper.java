package com.br.senac.apiclientebackend.mappers;

import com.br.senac.apiclientebackend.entitys.Cliente;
import com.br.senac.apiclientebackend.modelo.ClienteRequest;
import com.br.senac.apiclientebackend.modelo.ClienteResponse;

public class ClienteMapper {

    public static ClienteResponse clienteResponseMapper(Cliente cliente){
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

    public static Cliente clienteMapper(ClienteRequest clienteRequest){
        Cliente out = new Cliente();
        out.setId(clienteRequest.getId());
        out.setNome(clienteRequest.getNome());
        out.setSobreNome(clienteRequest.getSobreNome());
        out.setDataNascimento(clienteRequest.getDataNascimento());
        out.setDocumento(clienteRequest.getDocumento());
        out.setEmail(clienteRequest.getEmail());
        out.setTelefone(clienteRequest.getTelefone());

        return out;
    }
}

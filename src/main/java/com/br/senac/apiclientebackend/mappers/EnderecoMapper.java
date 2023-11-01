package com.br.senac.apiclientebackend.mappers;

import com.br.senac.apiclientebackend.entitys.Cliente;
import com.br.senac.apiclientebackend.entitys.Endereco;
import com.br.senac.apiclientebackend.modelo.EnderecoRequest;
import com.br.senac.apiclientebackend.modelo.EnderecoResponse;

public class EnderecoMapper {
    public static EnderecoResponse enderecoResponseMapper(Endereco endereco){
        EnderecoResponse out = new EnderecoResponse();
        out.setId(endereco.getId());
        out.setRua(endereco.getRua());
        out.setBairro(endereco.getBairro());
        out.setCidade(endereco.getCidade());
        out.setNomeResponsavel(endereco.getNomeResponsavel());
        out.setEstado(endereco.getEstado());
        out.setClienteId(endereco.getCliente().getId());

        return out;
    }


}

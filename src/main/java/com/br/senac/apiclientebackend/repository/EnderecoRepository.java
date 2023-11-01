package com.br.senac.apiclientebackend.repository;

import com.br.senac.apiclientebackend.entitys.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("select a from endereco a where a.cliente.id = :idCliente")
    public List<Endereco> carregarEnderecosByClienteId
            (@Param("idCliente") Long idCliente);


}

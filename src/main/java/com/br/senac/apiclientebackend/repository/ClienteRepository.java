package com.br.senac.apiclientebackend.repository;

import com.br.senac.apiclientebackend.entitys.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from cliente c where c.emailEnviado = false")
        public List<Cliente> carregarClientesNaoEnviado();
}

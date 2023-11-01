package com.br.senac.apiclientebackend.repository;

import com.br.senac.apiclientebackend.entitys.Emails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailsRepository extends JpaRepository<Emails, Long> {

}

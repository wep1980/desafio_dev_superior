package br.com.desafiodevsuperior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafiodevsuperior.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}

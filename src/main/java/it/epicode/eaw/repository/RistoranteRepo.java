package it.epicode.eaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.eaw.models.Ristorante;

public interface RistoranteRepo extends JpaRepository<Ristorante, Long> {

}

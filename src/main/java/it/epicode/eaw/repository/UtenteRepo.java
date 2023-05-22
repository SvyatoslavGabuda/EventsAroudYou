package it.epicode.eaw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import it.epicode.eaw.models.Utente;

public interface UtenteRepo extends JpaRepository<Utente, Long> {
	
	Optional<Utente> findByEmail(String email);

    Optional<Utente> findByUsernameOrEmail(String username, String email);

    Optional<Utente> findByUsername(String username);

    Boolean existsByUsername(String username);
 
    Boolean existsByEmail(String email);
}

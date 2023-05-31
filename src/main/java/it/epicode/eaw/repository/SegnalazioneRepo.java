package it.epicode.eaw.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.eaw.models.Segnalazione;

public interface SegnalazioneRepo extends JpaRepository<Segnalazione, Long>{
	
	Page<Segnalazione> findByArchiviato(Pageable pag,boolean archiviato);

	

}

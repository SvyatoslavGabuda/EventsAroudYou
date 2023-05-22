package it.epicode.eaw.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.epicode.eaw.models.LuogoDiInteresse;

public interface LuogoRepo extends JpaRepository<LuogoDiInteresse, Long> {
	List<LuogoDiInteresse> findByTitleContains(String title);

	Page<LuogoDiInteresse> findByTitleContains(Pageable pag, String title);

	@Query(value = "SELECT e FROM LuogoDiInteresse e WHERE e.indirizzzo "
			+ " IN (Select i FROM Indirizzo i WHERE i.citta LIKE %:citta%)")
	public List<LuogoDiInteresse> findEventoByCitta(String citta);
}

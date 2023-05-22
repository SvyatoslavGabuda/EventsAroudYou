package it.epicode.eaw.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.epicode.eaw.enums.EventType;
import it.epicode.eaw.models.Evento;

public interface EventoRepo extends JpaRepository<Evento, Long> {

	List<Evento> findByDuration(int duration);

	Page<Evento> findByDuration(Pageable pag, int duration);

	List<Evento> findByStartDate(LocalDateTime startDate);

	Page<Evento> findByStartDate(Pageable pag, LocalDateTime startDate);

	Page<Evento> findByTipoEvento(Pageable pag, EventType tipoEvento);

	Page<Evento> findBySponsored(Pageable pag, boolean sponsored);

	Page<Evento> findByTipoEventoAndStartDate(Pageable pag, EventType tipoEvento, LocalDateTime startDate);

	@Query("SELECT e FROM Evento e WHERE  e.bloccato = :val")
	Page<Evento> findByBloccati(Pageable pag, boolean val);

	@Query("SELECT e FROM Evento e WHERE LOWER(e.indirizzzo.citta) LIKE %:cittaProvincia% "
			+ "OR  LOWER(e.indirizzzo.provincia) LIKE %:cittaProvincia% AND e.bloccato = false")
	Page<Evento> findByCittaOrProvincia(Pageable pag, String cittaProvincia);

	@Query("SELECT e FROM Evento e WHERE(LOWER(e.indirizzzo.citta) LIKE %:cittaProvincia% "
			+ "OR LOWER(e.indirizzzo.provincia) LIKE %:cittaProvincia%) "
			+ "AND e.bloccato = false AND e.startDate BETWEEN :startDate AND :endDate")
	Page<Evento> findBydCittaOrProvinciaAndStartDateBetween(Pageable pag, String cittaProvincia,
			LocalDateTime startDate, LocalDateTime endDate);

	@Query("SELECT e FROM Evento e WHERE (LOWER(e.title) LIKE %:titolo% " +
	"AND (LOWER(e.indirizzzo.citta) LIKE %:cittaProvincia% "
			+ "OR LOWER(e.indirizzzo.provincia) LIKE %:cittaProvincia%)) "
			+ "AND e.bloccato = false AND e.startDate BETWEEN :startDate AND :endDate")
	Page<Evento> findByTitoloAndCittaOrProvinciaAndStartDateBetween(Pageable pag, String titolo, String cittaProvincia,
			LocalDateTime startDate, LocalDateTime endDate);

	
	@Query("SELECT e FROM Evento e WHERE LOWER(e.title) LIKE %:titolo% "
	        + "AND (LOWER(e.indirizzzo.citta) LIKE %:cittaProvincia% "
	        + "OR LOWER(e.indirizzzo.provincia) LIKE %:cittaProvincia%) "
	        + "AND e.bloccato = false")
	Page<Evento> findByTitoloAndCittaOrProvincia(Pageable pag, String titolo, String cittaProvincia);



	@Query("SELECT e FROM Evento e WHERE e.tipoEvento = :tipoEvento "
			+ "AND (e.indirizzzo.citta LIKE %:cittaProvincia% " + "OR e.indirizzzo.provincia LIKE %:cittaProvincia%) "
			+ "AND e.bloccato = false AND e.startDate BETWEEN :startDate AND :endDate")
	Page<Evento> findByTipoEventoAndCittaOrProvinciaAndStartDateBetween(Pageable pag, EventType tipoEvento,
			String cittaProvincia, LocalDateTime startDate, LocalDateTime endDate);

}

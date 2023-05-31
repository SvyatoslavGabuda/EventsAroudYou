package it.epicode.eaw.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import it.epicode.eaw.dto.EventoDto;
import it.epicode.eaw.enums.EventType;
import it.epicode.eaw.models.Evento;
import it.epicode.eaw.models.Indirizzo;
import it.epicode.eaw.service.EventoService;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*", maxAge = 6000000)
public class EventController {
	@Autowired
	EventoService eSer;
// Get Event By ID open
	@GetMapping("/info/{id}")
	public ResponseEntity<Evento> trovaEventobyId(@PathVariable Long id) {
		return new ResponseEntity<Evento>(eSer.findById(id), HttpStatus.OK);

	}
// Get only sponsored Event open to all
	@GetMapping("/sponsored")
	public ResponseEntity<Page<Evento>> findAllSponsoredEvent(Pageable pag) {
		Page<Evento> sponsoredEvents = eSer.findAllSponsoredEvent(pag);
		return new ResponseEntity<>(sponsoredEvents, HttpStatus.OK);
	}
// Get only sponsored Event open to all
	@GetMapping("/sponsored/{citta}")
	public ResponseEntity<Page<Evento>> findAllSponsoredEventByCity(Pageable pag,@PathVariable String citta) {
		Page<Evento> sponsoredEvents = eSer.findAllSponsoredByCitta(pag,citta);
		return new ResponseEntity<>(sponsoredEvents, HttpStatus.OK);
	}
// Get all Event
	@GetMapping()
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Evento>> trovaEventi() {
		return new ResponseEntity<List<Evento>>(eSer.findAllEvento(), HttpStatus.OK);

	}
// Get all Event Bloccati
	@GetMapping("/bloccati")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<Evento>> trovaEventiBloccati(Pageable pag) {
		return new ResponseEntity<Page<Evento>>(eSer.findAllEventiBloccati(pag), HttpStatus.OK);
		
	}
// Get Event By Citta or Provincia
	@GetMapping("/search/citta")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Page<Evento>> searchEventByCittaProvincia(@RequestParam String cittaProvincia,
			Pageable pageable) {
		Page<Evento> eventi = eSer.findByCittaProvincia(pageable, cittaProvincia);
		return new ResponseEntity<>(eventi, HttpStatus.OK);
	}
// Get Event By Citta or Provincia and titolot
	@GetMapping("/search/citta/titolo")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Page<Evento>> searchByTitoloAndCittaOrProvincia(@RequestParam String cittaProvincia,@RequestParam String titolo,
			Pageable pageable) {
		Page<Evento> eventi = eSer.findByTitoloAndCittaOrProvincia(pageable,titolo, cittaProvincia);
		return new ResponseEntity<>(eventi, HttpStatus.OK);
	}
// Get Event By Citta or Provincia and data di inizio between 2 dates
	@GetMapping("/search/citta/data")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Page<Evento>> searchEvent(

			@RequestParam String cittaProvincia,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
			Pageable pageable) {
		Page<Evento> eventi = eSer.findBydCittaOrProvinciaAndStartDateBetween(pageable, cittaProvincia, startDate, endDate);
		return new ResponseEntity<>(eventi, HttpStatus.OK);
	}
// get Event by citta or Provincia and Tipo di Evento and data between..
	@GetMapping("/search")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Page<Evento>> searchEvent(@RequestParam EventType tipoEvento,
			@RequestParam String cittaProvincia,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
			Pageable pageable) {
		Page<Evento> eventi = eSer.findByTipoEventoAndCittaOrProvinciaAndStartDateBetween(pageable, tipoEvento, cittaProvincia,
				startDate, endDate);
		return new ResponseEntity<>(eventi, HttpStatus.OK);
	}
// get Event by Title, citta or provincia, data 
	@GetMapping("/search/title")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Page<Evento>> searchEventByTitle(@RequestParam String titolo,
			@RequestParam String cittaProvincia,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
			Pageable pageable) {
		Page<Evento> eventi = eSer.findByTitoloAndCittaOrProvinciaAndStartDateBetween(pageable, titolo, cittaProvincia,
				startDate, endDate);
		return new ResponseEntity<>(eventi, HttpStatus.OK);
	}
// del Event by id
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> eliminaEventoByID(@PathVariable Long id) {
		return new ResponseEntity<String>(eSer.removeEvent(id), HttpStatus.OK);

	}
// Like/ not like 
	@PutMapping("/like/{id_user}/{id_evento}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> likeDaUtente(@PathVariable Long id_user, @PathVariable Long id_evento) {
		return new ResponseEntity<Evento>(eSer.likeDaUtente(id_user, id_evento), HttpStatus.OK);
	}
// blocca/sblocca evento
	@PutMapping("/blocc/{id_evento}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> bloccaEvento(@PathVariable Long id_evento) {
		return new ResponseEntity<Evento>(eSer.bloccaEvento( id_evento), HttpStatus.OK);
	}
// sponsorizza l'evento
	@PutMapping("/sponsor/{id_evento}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> sponsorizza(@PathVariable Long id_evento) {
		return new ResponseEntity<Evento>(eSer.sponsorizzaEvento(id_evento), HttpStatus.OK);
	}
// ---------------------------------------

// crea evento senza indirizzo ne immagine
	@PostMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> postaEvento(@RequestBody EventoDto e) {
		return new ResponseEntity<Evento>(eSer.saveEvento(e), HttpStatus.OK);
	}
	@PutMapping("/generali/{id_evento}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> modificaEvento(@RequestBody EventoDto e,@PathVariable Long id_evento) {
		return new ResponseEntity<Evento>(eSer.updateEvento(e,id_evento), HttpStatus.OK);
	}
// aggiunge un indirizzo al evento
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> postaEventoIndirizzo(@PathVariable Long id, @RequestBody Indirizzo i) {
		return new ResponseEntity<Evento>(eSer.updateEventoIndirizzo(id, i), HttpStatus.OK);
	}
// aggiunge un immagine al evento
	@PutMapping("/img/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> postaEventoImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		return new ResponseEntity<Evento>(eSer.updateEventoImmagine(id, file), HttpStatus.OK);
	}

}

package it.epicode.eaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import it.epicode.eaw.dto.SegnalazioneDto;
import it.epicode.eaw.models.Segnalazione;

import java.util.List;
import java.util.Optional;

import it.epicode.eaw.service.SegnalazioneService;

@RestController
@RequestMapping("/segnalazioni")
@CrossOrigin(origins = "*", maxAge = 6000000)
public class SegnalazioneController {
	@Autowired
	SegnalazioneService sSer;

	@PostMapping("/{id_u}/{id_luogo}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Segnalazione> saveSegnalazione(@RequestBody SegnalazioneDto segnalazione,
			@PathVariable Long id_u, @PathVariable Long id_luogo) {

		return new ResponseEntity<Segnalazione>(sSer.save(segnalazione,id_u,id_luogo), HttpStatus.CREATED);
	}
	@PutMapping("/stato/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Segnalazione> cambiaStatoSegnalazione(@PathVariable Long id) {
		
		return new ResponseEntity<Segnalazione>(sSer.cambiaStatoSegnalazione(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Segnalazione> updateSegnalazione(@PathVariable Long id,
			@RequestBody Segnalazione segnalazione) {
		Optional<Segnalazione> existingSegnalazione = sSer.findById(id);
		if (existingSegnalazione.isPresent()) {
			segnalazione.setId_segnalazione(id);
			Segnalazione updatedSegnalazione = sSer.update(segnalazione);
			return new ResponseEntity<>(updatedSegnalazione, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Segnalazione> getSegnalazioneById(@PathVariable Long id) {
		Optional<Segnalazione> segnalazione = sSer.findById(id);
		return segnalazione.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Segnalazione>> getAllSegnalazioni() {
		List<Segnalazione> segnalazioni = sSer.findAll();
		return new ResponseEntity<>(segnalazioni, HttpStatus.OK);
	}
	@GetMapping("/archiviato/{arc}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<Segnalazione>> getSegnalazioniByStato(Pageable pag,@PathVariable boolean arc) {		
		return new ResponseEntity<>(sSer.findByStato(pag, arc), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteSegnalazione(@PathVariable Long id) {
		Optional<Segnalazione> existingSegnalazione = sSer.findById(id);
		if (existingSegnalazione.isPresent()) {
			sSer.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

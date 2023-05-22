package it.epicode.eaw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.eaw.dto.CommentoDto;
import it.epicode.eaw.models.Commento;
import it.epicode.eaw.service.CommentoService;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*", maxAge = 6000000)
public class CommentoController {
	@Autowired
	CommentoService cSer;

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Commento> trovaEventobyId(@PathVariable Long id) {
		return new ResponseEntity<Commento>(cSer.findById(id), HttpStatus.OK);

	}

	@GetMapping()
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Commento>> trovaEventi() {
		return new ResponseEntity<List<Commento>>(cSer.getAllCommenti(), HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> eliminaEventoByID(@PathVariable Long id) {
		return new ResponseEntity<String>(cSer.deleteCommento(id), HttpStatus.OK);

	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> updateEvento(@PathVariable Long id, @RequestBody CommentoDto cont) {
		return new ResponseEntity<Commento>(cSer.updateCommento(id, cont), HttpStatus.OK);
	}

	@PostMapping(value = "/{id_u}/{id_l}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> postaEvento(@PathVariable Long id_u, @PathVariable Long id_l, @RequestBody CommentoDto cont) {
		return new ResponseEntity<Commento>(cSer.saveCommento(id_u, id_l, cont), HttpStatus.OK);
	}

}

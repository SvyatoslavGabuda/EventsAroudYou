package it.epicode.eaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.epicode.eaw.models.Utente;
import it.epicode.eaw.service.UtenteService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 6000000)
public class UtenteController {
	@Autowired
	UtenteService uSer;

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Utente> trovaEventobyId(@PathVariable Long id) {
		return new ResponseEntity<Utente>(uSer.findById(id), HttpStatus.OK);

	}
	
	@GetMapping("/username/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Utente> trovaEventobyUsername(@PathVariable String username) {
		return new ResponseEntity<Utente>(uSer.findByUsername(username), HttpStatus.OK);
		
	}
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> eliminaEventoByID(@PathVariable Long id) {
		return new ResponseEntity<String>(uSer.removeUtente(id), HttpStatus.OK);

	}
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> updateEvento(@RequestBody Utente u){
		return new ResponseEntity<Utente>(uSer.updateUtente(u),HttpStatus.OK);
	}
	
	@PutMapping("/uploadimage/{id}")	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")	
	public ResponseEntity<?> updateEvento(@PathVariable Long id,@RequestParam("file") MultipartFile file){
		return new ResponseEntity<Utente>(uSer.updateUtenteImage(id, file),HttpStatus.OK);
	}

}

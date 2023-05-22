package it.epicode.eaw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.epicode.eaw.auth.exception.MyAPIException;
import it.epicode.eaw.image.Image;
import it.epicode.eaw.image.ImageService;
import it.epicode.eaw.models.Utente;
import it.epicode.eaw.repository.UtenteRepo;

@Service
public class UtenteService {
	@Autowired
	UtenteRepo uRepo;
	@Autowired
	ImageService iSer;
	public Utente saveUtente(Utente e) {
		return uRepo.save(e);
	}

	public Utente updateUtente(Utente e) {
		return uRepo.save(e);
	}
	public Utente updateUtenteImage(Long iduser, MultipartFile file) {
		if(uRepo.existsById(iduser)) {
		Image i = iSer.saveImage(file);
		Utente u = uRepo.findById(iduser).get();
		u.setUrlImmagineProfilo(i.getUrl());
		
		return uRepo.save(u);}
		else {
			throw new MyAPIException(HttpStatus.NOT_FOUND, "utente non trovato");
		}
	}

	public String removeUtente(Long id) {
		uRepo.deleteById(id);
		return "Evento eliminato";
	}
	public Utente findById(Long id) {
		return uRepo.findById(id).get();
	}
	
	public Utente findByUsername(String username) {
		return uRepo.findByUsername(username).get();
	}

	public List<Utente> findAllUtente() {
		return uRepo.findAll();
	}
}

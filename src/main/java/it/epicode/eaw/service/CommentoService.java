package it.epicode.eaw.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.eaw.dto.CommentoDto;
import it.epicode.eaw.models.Commento;
import it.epicode.eaw.models.LuogoDiInteresse;
import it.epicode.eaw.models.Utente;
import it.epicode.eaw.repository.CommentoRepo;
import it.epicode.eaw.repository.LuogoRepo;

@Service
public class CommentoService {
	@Autowired
	CommentoRepo cRepo;
	@Autowired
	UtenteService uSer;
	@Autowired
	LuogoRepo lRepo;

	public Commento saveCommento(Long id_utente, Long id_luogo,CommentoDto contenuto) {
		Commento c = new Commento();
		Utente u = uSer.findById(id_utente);
		LuogoDiInteresse l = lRepo.findById(id_luogo).get();
		c.setContenuto(contenuto.getContenuto());
		c.setRaiting(contenuto.getRaitin());
		c.setTitoloCommento(contenuto.getTitoloCommento());
		c.setLuogoCommentato(l);
		c.setUtente(u);
		c.setDataCommento(LocalDateTime.now());
		Commento cs = cRepo.save(c);
		u.getCommenti().add(cs);
		uSer.updateUtente(u);
		l.getCommenti().add(cs);
		lRepo.save(l);
		return cs;
	}
	public String deleteCommento(long id) {
		Commento c = cRepo.findById(id).get();
		LuogoDiInteresse l = lRepo.findById(c.getLuogoCommentato().getIdLuogo()).get();
		l.getCommenti().remove(c);
		cRepo.deleteById(id);
		return"commento Eliminato";
	}
	public Commento updateCommento(Long id,CommentoDto cont) {
		Commento c = cRepo.findById(id).get();
		c.setTitoloCommento(cont.getTitoloCommento());
		c.setContenuto(cont.getContenuto());
		c.setRaiting(cont.getRaitin());
		return cRepo.save(c);
	}
	public Commento findById(Long id) {
		return cRepo.findById(id).get();
	}
	public List<Commento> getAllCommenti(){
		return cRepo.findAll();
	} 
}

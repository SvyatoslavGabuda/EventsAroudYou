package it.epicode.eaw.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.epicode.eaw.auth.exception.MyAPIException;
import it.epicode.eaw.dto.EventoDto;
import it.epicode.eaw.enums.EventType;
import it.epicode.eaw.image.Image;
import it.epicode.eaw.image.ImageService;
import it.epicode.eaw.models.Evento;
import it.epicode.eaw.models.Indirizzo;
import it.epicode.eaw.models.LuogoDiInteresse;
import it.epicode.eaw.models.Utente;
import it.epicode.eaw.repository.EventoRepo;
import it.epicode.eaw.repository.IndirizzoRepo;
import it.epicode.eaw.repository.LuogoRepo;
import it.epicode.eaw.repository.UtenteRepo;

@Service
public class EventoService {
	@Autowired
	UtenteRepo uRepo;
	@Autowired
	EventoRepo eRepo;
	@Autowired
	LuogoRepo lRepo;
	@Autowired
	IndirizzoRepo iRepo;
	@Autowired
	ImageService imageSer;

	public Evento saveEvento(EventoDto eDto) {
		System.out.println("sono nel service");
		if (uRepo.existsById(eDto.getCreatore())) {
			Utente u = uRepo.findById(eDto.getCreatore()).get();
			LuogoDiInteresse e = new Evento();
			e.setLat(eDto.getLat());
			e.setLng(eDto.getLng());
			e.setTitle(eDto.getTitle());
			e.setSubTitle(eDto.getSubTitle());
			e.setDescription(eDto.getDescription());
			((Evento) e).setDuration(eDto.getDuration());
			((Evento) e).setEndDate(eDto.getEndDate());
			((Evento) e).setStartDate(eDto.getStartDate());
//			((Evento) e).setCreatore(eDto.getCreatore());
			((Evento) e).setCreatore(u);
			((Evento) e).setNumMaxPartecipants(eDto.getNumMaxPartecipants());
			((Evento) e).setTipoEvento(EventType.GENERALE);
			return eRepo.save(((Evento) e));

		} else {
			throw new MyAPIException(HttpStatus.NOT_FOUND, "utente non valido");
		}
	}

	public Evento updateEvento(Evento e) {
		return eRepo.save(e);
	}
	public Evento likeDaUtente(Long id_user,Long id_evento) {
		Evento e = eRepo.findById(id_evento).get();
		Utente u = uRepo.findById(id_user).get();
		if(e.getLikeDaUtenti().contains(u)) {
			e.getLikeDaUtenti().remove(u);
		}else {
			
			e.getLikeDaUtenti().add(u);
		}
		eRepo.save(e);
		return e;
	}
	public Evento sponsorizzaEvento(Long id_evento) {
		Evento e = eRepo.findById(id_evento).get();
		e.setSponsored(true);
		eRepo.save(e);
		return e;
	}

	public Evento updateEventoIndirizzo(Long id_evento, Indirizzo i) {
//		Evento e = eRepo.findById(id_evento).get();
		Evento e = eRepo.findById(id_evento)
				.orElseThrow(() -> new MyAPIException(HttpStatus.NOT_FOUND, "Evento non trovato"));
		i.getLuogoDiInteresse().add(e);

		Indirizzo is = iRepo.save(i);
		e.setIndirizzzo(is);
		return eRepo.save(e);
	}

	public Evento updateEventoImmagine(Long id_evento, MultipartFile file) {
		Image i = imageSer.saveImage(file);
//		Evento e = eRepo.findById(id_evento).get();
		Evento e = eRepo.findById(id_evento)
				.orElseThrow(() -> new MyAPIException(HttpStatus.NOT_FOUND, "Evento non trovato"));
		e.setUrlImage(i.getUrl());
		return eRepo.save(e);
	}

	public String removeEvent(Long id) {
		if (eRepo.existsById(id)) {
			eRepo.deleteById(id);
			return "Evento eliminato";
		} else {
			throw new MyAPIException(HttpStatus.NOT_FOUND, "Evento non trovato");
		}
	}

	public Evento findById(Long id) {
		return eRepo.findById(id).orElseThrow(() -> new MyAPIException(HttpStatus.NOT_FOUND, "Evento non trovato"));
	}

	public List<Evento> findAllEvento() {
		return eRepo.findAll();
	}

	public List<Evento> findByNome(String nome) {
		List<LuogoDiInteresse> l = lRepo.findByTitleContains(nome);
		List<Evento> es = new ArrayList<Evento>();
		l.forEach(a -> es.add((Evento) a));
		return es;
	}

	public Page<Evento> findAllSponsoredEvent(Pageable pag) {
		return eRepo.findBySponsored(pag, true);
	}

	public Page<Evento> findByCittaProvincia(Pageable pag, String cittaProvincia) {
		return eRepo.findByCittaOrProvincia(pag, cittaProvincia);
	}
	public Page<Evento> findByTitoloAndCittaOrProvincia(Pageable pag,String titolo, String cittaProvincia) {
		return eRepo.findByTitoloAndCittaOrProvincia(pag,titolo, cittaProvincia);
	}

	public Page<Evento> findByTitoloAndCittaOrProvinciaAndStartDateBetween(Pageable pag, String titolo, String cittaProvincia,
			LocalDateTime startDate, LocalDateTime endDate) {
		return eRepo.findByTitoloAndCittaOrProvinciaAndStartDateBetween(pag, titolo, cittaProvincia, startDate, endDate);
	}

	public Page<Evento> findBydCittaOrProvinciaAndStartDateBetween(Pageable pag, String citta, LocalDateTime startDate,
			LocalDateTime endDate) {
		return eRepo.findBydCittaOrProvinciaAndStartDateBetween(pag, citta, startDate, endDate);
	}

	public Page<Evento> findByTipoEventoAndCittaOrProvinciaAndStartDateBetween(Pageable pag, EventType tipoEvento, String citta,
			LocalDateTime startDate, LocalDateTime endDate) {
		return eRepo.findByTipoEventoAndCittaOrProvinciaAndStartDateBetween(pag, tipoEvento, citta, startDate, endDate);
	}

}

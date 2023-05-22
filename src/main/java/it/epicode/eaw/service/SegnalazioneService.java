package it.epicode.eaw.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.epicode.eaw.repository.AttrazioneRepo;
import it.epicode.eaw.repository.EventoRepo;
import it.epicode.eaw.repository.HotelRepo;
import it.epicode.eaw.repository.LuogoRepo;
import it.epicode.eaw.repository.RistoranteRepo;
import it.epicode.eaw.repository.SegnalazioneRepo;
import it.epicode.eaw.repository.UtenteRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import it.epicode.eaw.dto.SegnalazioneDto;
import it.epicode.eaw.models.Attrazione;
import it.epicode.eaw.models.Evento;
import it.epicode.eaw.models.Hotel;
import it.epicode.eaw.models.LuogoDiInteresse;
import it.epicode.eaw.models.Ristorante;
import it.epicode.eaw.models.Segnalazione;
import it.epicode.eaw.models.Utente;

@Service
public class SegnalazioneService {
	@Autowired
	SegnalazioneRepo sRepo;
	@Autowired
	UtenteRepo uRepo;
	@Autowired
	LuogoRepo lRepo;
	@Autowired
	EventoRepo eRepo;
	@Autowired
	RistoranteRepo rRepo;
	@Autowired
	AttrazioneRepo aRepo;
	@Autowired
	HotelRepo hRepo;

	public Segnalazione save(SegnalazioneDto segnalazione, Long id_utente, Long id_luogo) {
		Utente u = uRepo.findById(id_utente).get();
		LuogoDiInteresse l = lRepo.findById(id_luogo).get();
		Segnalazione s = new Segnalazione();
		s.setContenuto(segnalazione.getContenuto());
		s.setUtenteSegnalatore(u);
		s.setDataSegnalazione(LocalDateTime.now());
		s.setArchiviato(false);
		if (l instanceof Evento) {
			Evento e = eRepo.findById(id_luogo).get();
			s.setCosaSegnalata(e);
		} else if (l instanceof Ristorante) {
			Ristorante r = rRepo.findById(id_luogo).get();
			s.setCosaSegnalata(r);
		} else if (l instanceof Attrazione) {
			Attrazione a = aRepo.findById(id_luogo).get();
			s.setCosaSegnalata(a);
		} else if (l instanceof Hotel) {
			Hotel h = hRepo.findById(id_luogo).get();
			s.setCosaSegnalata(h);
		}

		return sRepo.save(s);
	}

	public Segnalazione update(Segnalazione segnalazione) {
		return sRepo.save(segnalazione);
	}

	public Optional<Segnalazione> findById(Long id) {
		return sRepo.findById(id);
	}

	public List<Segnalazione> findAll() {
		return sRepo.findAll();
	}

	public Page<Segnalazione> findAll(Pageable pageable) {
		return sRepo.findAll(pageable);
	}

	public void deleteById(Long id) {
		sRepo.deleteById(id);
	}
}

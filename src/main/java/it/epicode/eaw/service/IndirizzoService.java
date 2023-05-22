package it.epicode.eaw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.eaw.models.Indirizzo;
import it.epicode.eaw.repository.IndirizzoRepo;

@Service
public class IndirizzoService  {
@Autowired
IndirizzoRepo iRepo;
public Indirizzo saveIndirizzo(Indirizzo i) {
	return iRepo.save(i);
}

public Indirizzo updateIndirizzo(Indirizzo i) {
	return iRepo.save(i);
}

public String removeIndirizzo(Long id) {
	iRepo.deleteById(id);
	return "indirizzo eliminato";
}
public Indirizzo findById(Long id) {
	return iRepo.findById(id).get();
}



public List<Indirizzo> findAllIndirizzi() {
	return iRepo.findAll();
}
}

package it.epicode.eaw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.eaw.repository.LuogoRepo;
import it.epicode.eaw.repository.RistoranteRepo;

@Service
public class RisotranteService {
	@Autowired
	RistoranteRepo rRepo;
	@Autowired
	LuogoRepo lRepo;
}

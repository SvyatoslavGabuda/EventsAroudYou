package it.epicode.eaw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.eaw.repository.HotelRepo;
import it.epicode.eaw.repository.LuogoRepo;

@Service
public class HotelService {
	@Autowired
	HotelRepo hRepo;
	@Autowired
	LuogoRepo lRepo;
}

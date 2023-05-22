package it.epicode.eaw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.eaw.repository.AttrazioneRepo;
import it.epicode.eaw.repository.LuogoRepo;

@Service
public class AttrazioneService {
@Autowired
AttrazioneRepo aRepo;
@Autowired
LuogoRepo lRepo;
}

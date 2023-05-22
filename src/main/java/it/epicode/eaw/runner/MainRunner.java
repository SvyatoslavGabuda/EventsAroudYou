package it.epicode.eaw.runner;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.epicode.eaw.auth.entity.ERole;
import it.epicode.eaw.auth.service.AuthServiceImpl;

import it.epicode.eaw.service.CommentoService;
import it.epicode.eaw.service.EventoService;
import it.epicode.eaw.service.IndirizzoService;

@Component
public class MainRunner implements ApplicationRunner{

	@Autowired
	EventoService eser;
	@Autowired
	AuthServiceImpl aS;
	@Autowired
	CommentoService cSer;
	@Autowired
	IndirizzoService iSer;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		

//		aS.changePermissions(1, ERole.ROLE_ADMIN);
		//cSer.saveCommento(3l, 1l);
	}

}

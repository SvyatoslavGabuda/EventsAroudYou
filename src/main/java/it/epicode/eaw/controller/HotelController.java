package it.epicode.eaw.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@CrossOrigin(origins = "*", maxAge = 6000000)
public class HotelController {

}

package it.epicode.eaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import it.epicode.eaw.models.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, Long>{

}

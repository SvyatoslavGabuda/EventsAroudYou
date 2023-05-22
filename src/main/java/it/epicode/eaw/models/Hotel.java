package it.epicode.eaw.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "hotels")
public class Hotel extends LuogoDiInteresse {
	private int stars;
	@OneToMany
	private List<ServizioHotel> services;
}

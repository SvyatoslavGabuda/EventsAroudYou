package it.epicode.eaw.dto;

import java.time.LocalDateTime;

import it.epicode.eaw.models.Indirizzo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventoDto {

	private Double lat;
	private Double lng;
	private String title;
	private String subTitle;
	private String description;
	private Indirizzo indirizzzo;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int duration;
	private int numMaxPartecipants;
	private Long creatore;
//	private Utente creatore;
}

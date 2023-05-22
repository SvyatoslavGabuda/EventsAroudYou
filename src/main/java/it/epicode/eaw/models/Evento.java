package it.epicode.eaw.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.epicode.eaw.enums.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "eventi")
public class Evento extends LuogoDiInteresse {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int duration;
	private int numMaxPartecipants;
	private boolean sponsored;
	private EventType tipoEvento;
	@ManyToOne
	@JsonIgnoreProperties(value = "eventiCreati")
	private Utente creatore;

	@Override
	public String toString() {
		return "Evento [startDate=" + startDate + ", endDate=" + endDate + ", startTime="  + ", duration="
				+ duration + ", numMaxPartecipants=" + numMaxPartecipants + ", getIdLuogo()=" + getIdLuogo()
				+ ", getLat()=" + getLat() + ", getLng()=" + getLng() + ", getTitle()=" + getTitle()
				+ ", getSubTitle()=" + getSubTitle() + ", getDescription()=" + getDescription() + "]";
	}

}

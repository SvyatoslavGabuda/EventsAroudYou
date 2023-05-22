package it.epicode.eaw.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "segnalazioni")
public class Segnalazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_segnalazione;
	private String contenuto;
	private LocalDateTime dataSegnalazione;
	private boolean archiviato;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = { "commenti", "eventiCreati", "likeDaUtenti", "segnalazioni" })
	private Utente utenteSegnalatore;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = { "commenti", "likeDaUtenti", "creatore" })
	private LuogoDiInteresse cosaSegnalata;
}

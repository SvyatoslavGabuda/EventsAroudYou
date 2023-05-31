package it.epicode.eaw.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "commenti")
public class Commento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCommento;
	private String titoloCommento;
	@Column(length = 2000)
	private String contenuto;
	private int raiting;
	private LocalDateTime dataCommento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"commenti","eventiCreati","likes","segnalazioni","roles","password"})
	private Utente utente;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
	@JsonIgnoreProperties(value = {"commenti","likeDaUtenti","creatore"})
    private LuogoDiInteresse luogoCommentato;
	

}

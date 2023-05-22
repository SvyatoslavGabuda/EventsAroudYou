package it.epicode.eaw.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class LuogoDiInteresse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idLuogo;
	private Double lat;
	private Double lng;
	@Column(nullable = false, unique = true)
	private String title;
	private String subTitle;
	@Column(length = 500)
	private String description;
	private String urlImage;
	private int raiting;
	private boolean bloccato;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = "luogoDiInteresse")
	private Indirizzo indirizzzo;
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"luogoCommentato"})
	private List<Commento> commenti = new ArrayList<Commento>();
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"likes","commenti","eventiCreati"})
	private Set<Utente> likeDaUtenti = new HashSet<Utente>();
	@OneToMany(mappedBy="cosaSegnalata",cascade = CascadeType.ALL)
    @JsonIgnore    
    private Set<Segnalazione> segnalazioni = new HashSet<Segnalazione>();

}

package it.epicode.eaw.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.epicode.eaw.auth.entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "utenti")
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUtente;
	@Column(nullable = false, unique = true)
	private String username;
	private String name;
	private String lastname;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	private LocalDate dataReggistrazione;
	
	private String urlImmagineProfilo;
	
	@OneToMany(mappedBy = "creatore",fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"creatore"})
    private List<Evento> eventiCreati = new ArrayList<Evento>();
    
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = "likeDaUtenti")
    private List<LuogoDiInteresse> likes = new ArrayList<LuogoDiInteresse>() ;
   
    @OneToMany(mappedBy = "utente",fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "utente")
    private List<Commento> commenti = new ArrayList<Commento>();
    
    @OneToMany(mappedBy="utenteSegnalatore")
    @JsonIgnoreProperties(value = "utenteSegnalatore")
    private List<Segnalazione> segnalazioni = new ArrayList<Segnalazione>();
	
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Set<Role> roles = new HashSet<>();

}

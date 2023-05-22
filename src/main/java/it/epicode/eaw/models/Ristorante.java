package it.epicode.eaw.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "resturant")
public class Ristorante extends LuogoDiInteresse{
private String specialty;
//private List<LocalDate> openingDays;
private LocalDateTime openingHours;
private LocalDateTime closingHours;


}

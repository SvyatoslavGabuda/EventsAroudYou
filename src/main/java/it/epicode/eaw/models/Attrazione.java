package it.epicode.eaw.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "attrazioni")
public class Attrazione extends LuogoDiInteresse {
private String tipo;
}

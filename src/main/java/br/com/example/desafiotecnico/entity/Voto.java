package br.com.example.desafiotecnico.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString(onlyExplicitlyIncluded = true)
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@ToString.Include
	private Pauta pauta;
	@ToString.Include
	private String cpfAssociado;
	@ToString.Include
	private boolean voto;
}

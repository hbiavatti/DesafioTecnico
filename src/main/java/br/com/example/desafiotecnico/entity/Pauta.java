package br.com.example.desafiotecnico.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Pauta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, unique = true)
	@ToString.Include
	private String nome;
	private Date dataInicioVotacao;
	private long duracao = 1000;
	@Column(nullable = false)
	private boolean aberta;
	
}

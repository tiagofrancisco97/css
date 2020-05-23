package business;


import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.DATE;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Version;

/**
 * Entity implementation class for Entity: Sessao
 *
 */
@Entity

public class Sessao{

	
	/**
	 * Space primary key. Needed by JPA.
	 */
	@Id @GeneratedValue(strategy = SEQUENCE)
	private int id;
	
	@Version
	private int version;
	
	@Enumerated(STRING)
	@Column(nullable = false)
	private Dia dia;
	
	@Column(nullable = true)
	private int lotacao;

	
	@Column(nullable = false)
	@Temporal(DATE)
	private Calendar horaInicio;

	private int utentesInscritos;
	
	public Sessao() {
	}
   
	
	/**
	 * Cria uma sessao de uma aula
	 * 
	 * @param id id da sessao 
	 * @param dia dia em que ocorre
	 * @param nAlunos numero de utentes numa sessao
	 * @param hora de inicio da sessao
	 */
	public Sessao(Dia dia, int nAlunos, Calendar horaInicio) {
		this.dia=dia;
		this.lotacao = nAlunos;
		this.horaInicio=horaInicio;
	}
	
	/**
	 * Obtem dia da semana da sessao
	 * 
	 * @return dia da semana
	 */
	public Dia getDia() {
		return dia;
	}
	
	/**
	 * Obtem hora de inicio da sessao 
	 * 
	 * @return hora
	 */
	public Calendar getHoraInicio() {
		return horaInicio;
	}
	
	/**
	 * Obtem numero de utentes numa sessao
	 * 
	 * @return numero de utentes
	 */
	public int getnAlunos() {
		return this.lotacao;
	}
	
	
	/**
	 * Obtem numero de vagas numa sessao
	 * 
	 * @return numero de vagas
	 */
	public int getVagas() {
		return this.lotacao-this.utentesInscritos;
	}
	
	
	public int getId() {
		return id;
	}

	
	/**
	 * Obtem lotacao da sessao
	 * 
	 * @return lotacao
	 */
	public int getLotacao() {
		return lotacao;
	}


	/**
	 * Adiciona um utente � sessao
	 * 
	 */
	public void adicionaUtente() {
		this.utentesInscritos++;
	}
}

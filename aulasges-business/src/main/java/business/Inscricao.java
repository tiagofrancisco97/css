package business;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;




/**
 * Entity implementation class for Entity: Inscricao
 *
 */
@Entity

public abstract class Inscricao {


	/**
	 * Space primary key. Needed by JPA.
	 */
	@Id @GeneratedValue(strategy = SEQUENCE)
	private int id;
	


	@ManyToOne
	private Utente utente;
	
	
	public Inscricao() {
	}
	
	/**
	 * Cria uma inscricao
	 * 
	 * @param utente numero do utente
	 * @param aula aula a inscrever
	 */
	public Inscricao(Utente utente) {
		this.utente=utente;
	}
	

	public Utente getUtente() {
		return this.utente;
	}




	/**
	 * Calcula o custo de uma aula
	 * @param aula aula
	 * @return custo de uma aula
	 */
	public abstract double calculaCusto(double duracao, double precoHora);
   
}

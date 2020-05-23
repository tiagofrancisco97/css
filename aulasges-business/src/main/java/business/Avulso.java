package business;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Avulso extends Inscricao{
	
	
	
	
	@ManyToOne
	private Sessao sessao;

	/**
	 * Cria uma inscricao avulso
	 * 
	 * @param utente numero do utente
	 * @param aula aula a inscrever
	 */
	public Avulso(Utente utente, Sessao sessao) {
		super(utente);
		this.sessao = sessao;
	}
	
	public Avulso() {
		
	}
	
	public Sessao getSessao() {
		return sessao;
	}

	/**
	 * Calcula o custo de uma inscri��o avulso
	 * 
	 * @param aula aula a inscrever
	 * @return Pre�o a pagar pela aula
	 */
	@Override
	public double calculaCusto(double duracao, double precoHora) {
		double custo = precoHora* (duracao/60);
		return Math.round(custo * 100.0) / 100.0;
	}

}

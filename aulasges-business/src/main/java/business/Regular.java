package business;

import java.util.List;


import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Regular extends Inscricao{
	
	

	@OneToMany
	private List<Sessao> sessao;
	/**
	 * Cria uma inscricao regular
	 * 
	 * @param utente numero do utente
	 * @param aula aula a inscrever
	 */
	public Regular(Utente utente, List<Sessao> sessao) {
		super(utente);
		this.sessao=sessao;
	}
	
	public Regular() {
		
	}
	/**
	 * Calcula o custo de uma inscri��o regular
	 * 
	 * @param aula aula a inscrever
	 * @return Pre�o a pagar pela aula
	 */
	@Override
	public double calculaCusto(double duracao, double precoHora){
		double custo = precoHora*(duracao/60)*this.sessao.size()*4;
		return Math.round(custo * 100.0) / 100.0;
	}
}

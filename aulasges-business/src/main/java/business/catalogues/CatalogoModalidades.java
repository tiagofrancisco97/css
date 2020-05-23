package business.catalogues;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.Modalidade;
import facade.exceptions.ApplicationException;

@Stateless
public class CatalogoModalidades {
	
	@PersistenceContext
	private EntityManager em;
	
	/*public CatalogoModalidades(EntityManager em) {
		this.em=em;
	}*/
	
	/**
	 * Obtem uma modalidade com um dado nome
	 * 
	 * @param nome nome da modalidade a procurar
	 * @return modalidade com o nome nome
	 * @throws ApplicationException 
	 */
	public Modalidade getModalidadeByName (String nomeModalidade) throws ApplicationException {
		try {
			TypedQuery<Modalidade> query = em.createNamedQuery(Modalidade.FIND_BY_NAME, Modalidade.class);
			query.setParameter(Modalidade.MODALIDADE_NAME, nomeModalidade);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException ("Modalidade not found.");
		}
	}
	

	/**
	 * Obtem modalidades existentes
	 * 
	 * @return lista com o nome das modalidades existentes
	 * @throws ApplicationException 
	 */
	public List<String> getModalidades() throws ApplicationException {
		try {
			TypedQuery<String> query = em.createNamedQuery(Modalidade.GET_ALL_NAMES, String.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Nao existem modalidades disponiveis");
		}	
	}
	
	/**
	 * Verifica se a duracao � valida
	 * 
	 * @param duracao duracao da aula
	 * @param modalidade 
	 * @return Se a duracao � valida ou nao. 
	 */
	public boolean verificaDuracao(int duracao, Modalidade modalidade) {
		return duracao > 0 && duracao >= modalidade.getDuracaoMinima();
	}


}

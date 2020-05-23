package business.catalogues;



import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.Utente;
import facade.exceptions.ApplicationException;

@Stateless
public class CatalogoUsers {
	
	@PersistenceContext
	private EntityManager em;
	
	/*public CatalogoUsers(EntityManager em) {
		this.em=em;
	}*/
	
	
	/**
	 * Encontra um utente dado o seu numero
	 * @param numeroUtente numero de utente
	 * @return Utente
	 * @throws ApplicationException 
	 */
	public Utente getUtenteById(int numeroUtente) throws ApplicationException {
		try {
			TypedQuery<Utente> query = em.createNamedQuery(Utente.FIND_BY_ID, Utente.class);
			query.setParameter(Utente.UTENTE_ID, numeroUtente);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException ("Numero de utente invalido!");
		}
	}
}

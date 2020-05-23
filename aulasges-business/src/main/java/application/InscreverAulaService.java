package application;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.InscreverAulaHandler;
import facade.exceptions.ApplicationException;
import facade.handlers.IinscreverAulaServiceRemote;

@Stateless
public class InscreverAulaService implements IinscreverAulaServiceRemote {
	
	@EJB
	private InscreverAulaHandler inscreverAulaHandler;
	
	
	/**
     * Constructs a inscrever aula service given the inscrever aula handler.
     * 
     * @param inscreverAulaHandler 
     */
	/*public InscreverAulaService(InscreverAulaHandler inscreverAulaHandler) {
		this.inscreverAulaHandler = inscreverAulaHandler;
	}*/
	
	/**
	 * Inicia inscri��o numa aula
	 * 
	 * @throws ApplicationException 
	 */
	public List<String> iniciaInscricaoAula() throws ApplicationException {
		return this.inscreverAulaHandler.iniciaInscricaoAula();
	}

	/**
	 * Utente inscreve-se numa aula de uma modalidade,
	 * escolhendo o tipo de inscri��o
	 * 
	 * @param modalidade modalidade a da aula
	 * @param inscricao tipo de inscricao
	 * @throws ApplicationException 
	 */
	public List<facade.dto.Aula> aulasAtivas(String modalidade, String inscricao)throws ApplicationException {

		return this.inscreverAulaHandler.aulasAtivas(modalidade, inscricao);
	}

	/**
	 * Utente inscreve-se numa aula de uma modalidade,
	 * indicando numero de utente
	 * 
	 * @param aula nome da aula
	 * @param numeroUtente numero de utente
	 * @throws ApplicationException 
	 */
	public double inscricaoAula (String aula, int numeroUtente, String inscricao) throws ApplicationException{

		return this.inscreverAulaHandler.inscricaoAula(aula, numeroUtente, inscricao);
	}
	
}

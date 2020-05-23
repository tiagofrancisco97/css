package business.handlers;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.AulaAtiva;
import business.Modalidade;
import business.Utente;
import business.catalogues.CatalogoAulas;
import business.catalogues.CatalogoModalidades;
import business.catalogues.CatalogoUsers;
import facade.exceptions.ApplicationException;

@Stateless
public class InscreverAulaHandler {
	
	@EJB
	private CatalogoModalidades catalogoModalidades;
	
	@EJB
	private CatalogoAulas catalogoAulas;
	
	@EJB
	private CatalogoUsers catalogoUsers;
	//private EntityManagerFactory emf;

	//private String inscricao;

	/**
	 * Cria um handler de inscri��o na aula
	 *  
	 */
	/*public InscreverAulaHandler(EntityManagerFactory emf) {
		this.emf=emf;
	}*/


	/**
	 * Inicia inscri��o numa aula
	 *  
	 * @return Uma lista com o nome das modalidades existentes
	 * @throws ApplicationException 
	 */
	public List<String> iniciaInscricaoAula() throws ApplicationException {
		//EntityManager em = emf.createEntityManager();
		//CatalogoModalidades catalogoModalidades = new CatalogoModalidades(em);
		List<String> result = new ArrayList <>();
		try {
			//em.getTransaction().begin();
			result = catalogoModalidades.getModalidades();
			//em.getTransaction().commit();
		} catch (Exception e) {
			throw new ApplicationException("Error fetching aulas.", e);
		} /*finally {
			em.close();
		}*/
		return result;
	}

	/**
	 * Utente ve quais as aulas ativas,
	 * escolhendo o tipo de inscri��o
	 * 
	 * @param modalidade modalidade a da aula
	 * @param inscricao tipo de inscricao
	 * @throws ApplicationException 
	 */
	public List<facade.dto.Aula> aulasAtivas(String nomeModalidade, String inscricao)throws ApplicationException {
		//EntityManager em = emf.createEntityManager();
		//CatalogoModalidades catalogoModalidades = new CatalogoModalidades(em);
		//CatalogoAulas catalogoAulas = new CatalogoAulas(em);
		List<facade.dto.Aula> aulasAtivas = new ArrayList<>();
		
		try {
			//em.getTransaction().begin();
			Modalidade modalidade = catalogoModalidades.getModalidadeByName(nomeModalidade);
			
			if (!catalogoAulas.verificaTipoInscricao(inscricao)) {
				throw new ApplicationException ("Tipo de inscri��o invalido");
			} 

			for (AulaAtiva aula : catalogoAulas.aulasAtivas(modalidade, inscricao)) {
				aulasAtivas.add(new facade.dto.Aula(aula.getAula().getNome(), 
						aula.getAula().getDiasDaSemana(), aula.getAula().getHoraDeInicio(), 
						 aula.getInstalacao(),  aula.numeroVagas(inscricao)));
			}
			//this.inscricao = inscricao;
			//em.getTransaction().commit();
			
		} catch (Exception e) {
			throw new ApplicationException("Erro ao mostrar aulas ativas", e);
		} /*finally {
			em.close();
		}*/
		return aulasAtivas;
		
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
		//EntityManager em = emf.createEntityManager();
		//CatalogoUsers catalogoUsers = new CatalogoUsers(em);
		//CatalogoAulas catalogoAulas = new CatalogoAulas(em);
		double custo;
		
		try {
			//em.getTransaction().begin();
			Utente utente = catalogoUsers.getUtenteById(numeroUtente);
			
			AulaAtiva aulaAtiva = catalogoAulas.obtemAulaAtiva(aula);
			
			if (!aulaAtiva.verificaSessao(inscricao)) {
				throw new ApplicationException ("Nao existem vagas nesta aula!");
			}
			custo = catalogoAulas.inscreveUtente(inscricao, aulaAtiva, utente);
			//em.getTransaction().commit();
			
		} catch (Exception e) {
			/*if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}*/
			throw new ApplicationException("Erro ao inscrever utente em aula", e);
		} /*finally {
			em.close();
		}*/
		return custo;
	}

}

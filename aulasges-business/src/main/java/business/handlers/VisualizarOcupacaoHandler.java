package business.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.AulaAtiva;
import business.catalogues.CatalogoAulas;
import business.catalogues.CatalogoInstalacoes;
import business.utils.DateUtils;
import facade.exceptions.ApplicationException;

@Stateless
public class VisualizarOcupacaoHandler {
		
	//private EntityManagerFactory emf;
	
	@EJB
	private CatalogoInstalacoes ci;
	
	@EJB
	private CatalogoAulas ca;
	
	/**
	 * Cria um handler de visualiza��o de ocupa��o das aulas
	 *  
	 * @param catalogoAulas Catalogo das aulas
	 * @param catalogoInstalacoes Catalogo das instala��es
	 */
	/*public VisualizarOcupacaoHandler(EntityManagerFactory emf) {
		this.emf=emf;
	}*/
	
	/**
	 * Obtem aulas numda dada instala��o
	 * @param instalacao nome da instala��o
	 * @param data data atual
	 * @return lista de entradas com as aulas que decorrem na instala��o
	 * @throws ApplicationException
	 */
	public List<facade.dto.Aula> visualizarOcupacao(String instalacao, Calendar data) throws ApplicationException {
		
		//EntityManager em = this.emf.createEntityManager();
		//CatalogoInstalacoes ci = new CatalogoInstalacoes(em);
		//CatalogoAulas ca = new CatalogoAulas(em);
		List <AulaAtiva> aulas = new ArrayList<>();
		
		try {
			//em.getTransaction().begin();
			
			aulas =ca.getOcupacaoInstalacao(ci.getInstalacao(instalacao), 
					data, DateUtils.converte(data.get(Calendar.DAY_OF_WEEK)));
			
			//em.getTransaction().commit();
		} catch (Exception e) {
			throw new ApplicationException("Erro ao obter as aulas na intalacao "+instalacao,e);
		} /*finally {
			em.close();
		}*/
				
		List<facade.dto.Aula> entradas = new ArrayList<>();
		for (AulaAtiva aula : aulas) {
			entradas.add(new facade.dto.Aula(aula.getAula().getNome(), aula.getInicio(),
						aula.getFim(),aula.getAula().getHoraDeInicio()));
		}
		entradas.sort(Comparator.comparing(facade.dto.Aula::getHora));
		return entradas;
	}
	
	public List<String> instalacoes() throws ApplicationException{
		return this.ci.getInstalacoes();
	}
	
	
}

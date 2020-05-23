package business.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.Aula;
import business.Dia;
import business.Instalacao;
import business.Modalidade;
import business.catalogues.CatalogoAulas;
import business.catalogues.CatalogoInstalacoes;
import business.catalogues.CatalogoModalidades;
import facade.exceptions.ApplicationException;

@Stateless
public class AulaHandler {

	//private EntityManagerFactory emf;

	@EJB
	private CatalogoModalidades catalogoModalidades;
	
	@EJB
	private CatalogoAulas catalogoAulas;
	
	@EJB
	private CatalogoInstalacoes catalogoInstalacoes;

	/**
	 * Cria um handler da aula
	 *  
	 * @param catalogoAulas Catalogo das aulas
	 * @param catalogoInstalacoes Catalogo das instala��es
	 * @param catalogoModadalidades Catalogo das modalidades
	 */
	/*public AulaHandler(EntityManagerFactory emf) {
		this.emf=emf;
	}*/


	/**
	 * Inicia a cria��o de uma aula
	 * @return Uma lista com o nome das modalidades existentes
	 * @throws ApplicationException 
	 */
	public List<String> iniciaCriacaoAula() throws ApplicationException {
		//EntityManager em = emf.createEntityManager();
		//CatalogoModalidades catalogoModalidades = new CatalogoModalidades(em);
		List<String> result = new ArrayList <>();
		try {
			//em.getTransaction().begin();
			result = catalogoModalidades.getModalidades();
			//em.getTransaction().commit();
		} catch (Exception e) {
			throw new ApplicationException("Erro ao obter modalidades.", e);

		} /*finally {
			em.close();
		}*/
		return result;
	}

	/**
	 * Cria uma numa nova aula com modalidade, nome e duracao validos
	 * 
	 * @param modalidade  modalidade a adicionar
	 * @param nome nome da aula
	 * @param diasSemana dias da semana
	 * @param horaInicio hora de inicio da aula
	 * @param duracao duracao da aula
	 * @throws ApplicationException 
	 */
	public void criaAula(String nomeModalidade, String nome, List<Dia> diasSemana, 
			Calendar horaInicio, int duracao)throws ApplicationException {

		//EntityManager em = emf.createEntityManager();
		//CatalogoModalidades catalogoModalidades = new CatalogoModalidades(em);
		//CatalogoAulas catalogoAulas = new CatalogoAulas(em);

		try {
			//em.getTransaction().begin();
			Modalidade modalidade = catalogoModalidades.getModalidadeByName(nomeModalidade);

			if(!catalogoModalidades.verificaDuracao(duracao, modalidade)) {
				throw new ApplicationException ("Dura��o invalida na cria��o da aula "+nome);
			}
			catalogoAulas.addAula(modalidade, nome, diasSemana, horaInicio, duracao);

			//em.getTransaction().commit();

		} catch (Exception e) {
			/*if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}*/
			throw new ApplicationException("Erro ao criar aula "+nome, e);
		} /*finally {
			em.close();
		}*/
	}


	/**
	 * Inicia ativa��o da aula
	 * 
	 * @throws ApplicationException 
	 */
	public List<String> iniciarAtivacaoAula() throws ApplicationException {
		//EntityManager em = this.emf.createEntityManager();
		//CatalogoInstalacoes ci = new CatalogoInstalacoes(em);
		List<String> instalacoes = new ArrayList<>();
		try {
			//em.getTransaction().begin();
			instalacoes = catalogoInstalacoes.getInstalacoes();
			//em.getTransaction().commit();

		} catch (Exception e) {
			throw new ApplicationException("Erro ao obter o nome das instala��es", e);
		} /*finally {
			em.close();
		}*/
		return instalacoes;

	}

	/**
	 * O gestor ativa a aula
	 * 
	 * @param nomeAula nome da aula
	 * @param lotacao numero maximo de participantes
	 * @param instalacao nome da instala��o
	 * @param inicio data de inicio da aula
	 * @param fim data de fim da aula
	 * @param numMaxAlunos numero maximo de alunos na aula
	 * @throws ApplicationException 
	 */
	public void ativaAula(String nomeAula, String instalacao,
			Calendar inicio, Calendar fim, int numMaxAlunos) throws ApplicationException {

		//EntityManager em =this.emf.createEntityManager();
		//CatalogoAulas catalogoAulas = new CatalogoAulas(em);		
		//CatalogoInstalacoes catalogoInstalacoes = new CatalogoInstalacoes(em);

		try {
			//em.getTransaction().begin();

			Aula aula = catalogoAulas.getAula(nomeAula);
			Instalacao inst=catalogoInstalacoes.getInstalacao(instalacao);
			catalogoInstalacoes.verificaInstalacao(aula, inst, aula.getModalidade().getNome(),
					aula.getDiasDaSemana(), aula.getHoraDeInicio(), aula.getDuracao(),
					inicio, fim, numMaxAlunos);
			catalogoAulas.addAulaAtiva(aula, numMaxAlunos, inst, inicio, fim);
			inst.adicionaAula(catalogoAulas.obtemAulaAtiva(nomeAula));
			//em.merge(inst);

			//em.getTransaction().commit();
		} catch (Exception e) {
			/*if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}*/
			throw new ApplicationException("Erro ao ativar aula!", e);
		} /*finally {
			em.close();
		}*/
	}

}

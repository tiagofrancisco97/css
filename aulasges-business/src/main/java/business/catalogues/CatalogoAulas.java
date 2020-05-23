package business.catalogues;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.Aula;
import business.AulaAtiva;
import business.Dia;
import business.Inscricao;
import business.Instalacao;
import business.Modalidade;
import business.Sessao;
import business.Utente;
import business.utils.DateUtils;
import facade.exceptions.ApplicationException;

@Stateless
public class CatalogoAulas {
	
	@PersistenceContext
	private EntityManager em;

	/*public CatalogoAulas(EntityManager em) {
		this.em =em;
	}*/

	/**
	 * Obtem uma aula com um dado nome
	 * @param nome nome da aula a procurar
	 * @throws ApplicationException 
	 * @return aula com o nome nome
	 */
	public Aula getAula (String nome) throws ApplicationException {
		try {
			TypedQuery<Aula> query = em.createNamedQuery(Aula.FIND_BY_NAME, Aula.class);
			query.setParameter(Aula.AULA_NAME, nome);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException("Aula com o nome " + nome + " n�o existe.");
		}
	}

	public AulaAtiva obtemAulaAtiva(String nome) throws ApplicationException {
		try {
			TypedQuery<AulaAtiva> query = em.createNamedQuery(AulaAtiva.AA_FIND_BY_NAME, AulaAtiva.class);
			query.setParameter(AulaAtiva.AULAATIVA_NAME, nome);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException("Aula com o nome " + nome + " n�o existe.");
		}
	}


	/**
	 * Adiciona uma aula ativa
	 * 
	 * @param modalidade  modalidade a adicionar
	 * @param nome nome da aula
	 * @param diasSemana dias da semana
	 * @param horaInicio hora de inicio da aula
	 * @param duracao duracao da aula
	 * @param instalacao
	 * @param inicio
	 * @param fim
	 * @throws ApplicationException 
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addAulaAtiva(Aula aula, int lotacao,
			Instalacao instalacao, Calendar inicio, Calendar fim)  {
		AulaAtiva ativa = new AulaAtiva(aula, lotacao, instalacao, inicio, fim);
		for (Sessao sessao :  ativa.getSessoes()) {
			this.em.persist(sessao);
		}
		this.em.persist(ativa);
	}

	/**
	 * Adiciona uma aula
	 * 
	 * @param modalidade  modalidade a adicionar
	 * @param nome nome da aula
	 * @param diasSemana dias da semana
	 * @param horaInicio hora de inicio da aula
	 * @param duracao duracao da aula
	 * @throws ApplicationException 
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addAula(Modalidade modalidade, String nome, List<Dia> diasSemana, Calendar horaInicio,
			int duracao) throws ApplicationException {
		if (!Aula.verificaNome(nome)) {
			throw new ApplicationException ("Nome invalido");
		}
		Aula a = new Aula(nome, diasSemana, horaInicio, duracao, modalidade);
		em.persist(a);
	}


	/**
	 * Lista das aulas ativas dependendo do tipo de inscricao
	 * 
	 * @param modalidade modalidade da aula
	 * @param inscricao tipo de inscricao
	 * @throws ApplicationException 
	 */
	public List<AulaAtiva> aulasAtivas (Modalidade modalidade, String inscricao)throws ApplicationException{
		List <AulaAtiva> aulasAtivas = new ArrayList<>();
		try {
			TypedQuery<AulaAtiva> query = em.createNamedQuery(AulaAtiva.FIND_BY_MODALIDADE, AulaAtiva.class);
			query.setParameter(AulaAtiva.MODALIDADE_ID, modalidade);
			aulasAtivas =  query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Nao existem aulas ativas disponiveis");
		}	

		List <AulaAtiva> ativas = new ArrayList<>();
		for (AulaAtiva a : aulasAtivas) {
			if(a.verificaSessao(inscricao)){
				Calendar atual= DateUtils.getAtual();
				if(!atual.after(a.dataUltimaSessao())) {
					ativas.add(a);
				}
			}
		}
		return ativas;
	}

	/**
	 * Inscreve o utente nas sessoes da aula
	 * 
	 * @param inscricao tipo de inscricao
	 * @param aulaAtiva aula a inscrever
	 * @param utente numero do utente
	 * @param em Entity Manager
	 * @return 
	 * @throws ApplicationException 
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public double inscreveUtente(String tipoInscricao, AulaAtiva aulaAtiva, Utente utente){
		Inscricao inscricao = aulaAtiva.inscreveUtenteSessoes(tipoInscricao, utente);
		em.persist(inscricao);
		return inscricao.calculaCusto(aulaAtiva.getAula().getDuracao(), aulaAtiva.getAula().getPrecoHora());
	}


	public List<AulaAtiva> getOcupacaoInstalacao(Instalacao instalacao, Calendar data, Dia dia) throws ApplicationException {

		try {
			TypedQuery<AulaAtiva> query = em.createNamedQuery(AulaAtiva.FIND_BY_INSTALACAO, AulaAtiva.class);
			query.setParameter(AulaAtiva.INSTALACAO_ID, instalacao);
			query.setParameter(AulaAtiva.GIVEN_DATE, data);
			query.setParameter(AulaAtiva.GIVEN_DAY, dia);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException("N�o existe aula nesta instala��o.");
		}

	}


	/**
	 * Verifica se o tipo de inscricao � valido
	 * @param inscricao 
	 * 
	 * @return Se a inscricao � valida
	 */
	public boolean verificaTipoInscricao(String inscricao) {
		return inscricao.equals("regular") || inscricao.equals("avulso");
	}
}

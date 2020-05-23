package business.catalogues;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.Aula;
import business.Dia;
import business.Instalacao;
import business.utils.DateUtils;
import facade.exceptions.ApplicationException;

@Stateless
public class CatalogoInstalacoes {
	
	@PersistenceContext
	private EntityManager em;
	
	/*public CatalogoInstalacoes(EntityManager em) {
		this.em =em;
	}*/
	
	/**
	 * Obtem uma instala��o com um dado nome
	 * @param nome nome da instala��o a procurar
	 * @return instala��o com o nome nome
	 * @throws ApplicationException 
	 */
	public Instalacao getInstalacao (String nome) throws ApplicationException {
		try {
			TypedQuery<Instalacao> query = em.createNamedQuery(Instalacao.FIND_BY_NAME, Instalacao.class);
			query.setParameter(Instalacao.INSTALACAO_NAME, nome);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException("Instala��o com o nome: " + nome + " n�o existe.");
		}
	}
	

	/**
	 * Obtem instala��es no catalogo
	 * @return um Set<String> com as instala��es existentes
	 * @throws ApplicationException 
	 */
	public List<String> getInstalacoes() throws ApplicationException {
		try {
			TypedQuery<String> query = em.createNamedQuery(Instalacao.GET_ALL_NAMES, String.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Erro ao obter instala��es");
		}
	}

	/**
	 * Verifica dados da aula a adicionar a uma instala��o
	 * 
	 * @param aula 
	 * @param instalacao instala��o � qual a aula vai ser adicionada
	 * @param modalidade modalidade atribuida a aula a ser adicionada
	 * @param diasDaSemana Dias da semana em que a aula vai ocorrer
	 * @param horaDeInicio Hora de inicio da aula
	 * @param duracao dura��o da aula
	 * @param inicio Periodo inicial
	 * @param fim Periodo final
	 * @param numMaxAlunos numero max de alunos que uma dada aula pode ter
	 * @throws ApplicationException 
	 */
	public void verificaInstalacao(Aula aula, Instalacao instalacao, String modalidade, List<Dia> diasDaSemana, Calendar horaDeInicio,
			int duracao, Calendar inicio, Calendar fim, int numMaxAlunos) throws ApplicationException {
		
		if(!inicio.after(DateUtils.getAtual()) && !fim.after(inicio)) {
			throw new ApplicationException("Periodo de inicio e fim n�o v�lido!");
		}
		if(!instalacao.contemModalidade(modalidade))  {
			throw new ApplicationException("A instala��o n�o permite esse tipo de modalidade");
		}
		if(!instalacao.estaDisponivel(diasDaSemana, horaDeInicio,duracao,inicio,fim)) {
			throw new ApplicationException("A instala��o n�o est� disponivel neste hor�rio/dias de semana!");
		}
		if(numMaxAlunos>instalacao.getLotacaoMax()) {
			throw new ApplicationException("A instala��o n�o tem capacidade para esse n�mero de alunos!");
		}
	}
}

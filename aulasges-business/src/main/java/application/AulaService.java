package application;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.Dia;
import business.handlers.AulaHandler;
import facade.exceptions.ApplicationException;
import facade.handlers.IAulaServiceRemote;




@Stateless
public class AulaService implements IAulaServiceRemote{
	
	@EJB
	private AulaHandler aulaHandler;
	
	
	 /**
     * Constructs a aula service given the aula handler.
     * 
     * @param aulaHandler 
     */
	/*public AulaService(AulaHandler aulaHandler) {
		this.aulaHandler = aulaHandler;
	}*/
	
	/**
	 * Inicia a cria��o de uma aula
	 * 
	 * @throws ApplicationException 
	 */
	public List<String> iniciaCriacaoAula() throws ApplicationException {
		return this.aulaHandler.iniciaCriacaoAula();
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
	public void criaAula(String modalidade, String nome, List<Dia> diasSemana, 
			Calendar horaInicio, int duracao)throws ApplicationException {
	
		this.aulaHandler.criaAula(modalidade, nome, diasSemana, horaInicio, duracao);
	}
		
	
	/**
	 * Inicia ativa��o da aula
	 * 
	 * @throws ApplicationException 
	 */
	public List<String> iniciarAtivacaoAula() throws ApplicationException {
		return this.aulaHandler.iniciarAtivacaoAula();
	}
	
	/**
	 * Ativa��o da aula
	 * 
	 * @param nomeAula nome da aula
	 * @param instalacao nome da instala��o
	 * @param inicio data de inicio da aula
	 * @param fim data de fim da aula
	 * @param numMaxAlunos numero maximo de alunos na aula
	 * @throws ApplicationException 
	 */
	public void ativaAula(String nomeAula, String instalacao,
			Calendar inicio, Calendar fim, int numMaxAlunos) throws ApplicationException  {
		
		this.aulaHandler.ativaAula(nomeAula, instalacao, inicio, fim, numMaxAlunos);
	}
	
	
}

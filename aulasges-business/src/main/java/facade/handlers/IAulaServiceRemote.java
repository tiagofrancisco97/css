package facade.handlers;


import java.util.Calendar;
import java.util.List;

import javax.ejb.Remote;

import business.Dia;
import facade.exceptions.ApplicationException;

@Remote
public interface IAulaServiceRemote {
	
	public List<String> iniciaCriacaoAula() throws ApplicationException ;
	
	public void criaAula(String modalidade, String nome, List<Dia> diasSemana, 
			Calendar horaInicio, int duracao)throws ApplicationException;
		
	
	public List<String> iniciarAtivacaoAula() throws ApplicationException ;

	public void ativaAula(String nomeAula, String instalacao,
			Calendar inicio, Calendar fim, int numMaxAlunos) throws ApplicationException;
}

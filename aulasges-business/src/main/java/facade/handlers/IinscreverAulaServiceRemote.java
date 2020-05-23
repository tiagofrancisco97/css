package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import facade.exceptions.ApplicationException;

@Remote
public interface IinscreverAulaServiceRemote {
	
	
	public List<String> iniciaInscricaoAula() throws ApplicationException;
	
	public List<facade.dto.Aula> aulasAtivas(String modalidade, String inscricao)throws ApplicationException;
	
	public double inscricaoAula (String aula, int numeroUtente, String inscricao) throws ApplicationException;
}

package facade.handlers;


import java.util.Calendar;
import java.util.List;

import javax.ejb.Remote;

import facade.exceptions.ApplicationException;

@Remote
public interface IVisualizarOcupacaoServiceRemote {
	
	public List<facade.dto.Aula> visualizarOcupacao(String instalacao, Calendar data) throws ApplicationException;

	public List<String> instalacoes() throws ApplicationException;
}

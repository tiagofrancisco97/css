package facade.handlers;


import java.util.List;

import javax.ejb.Remote;

import facade.exceptions.ApplicationException;

@Remote
public interface IVisualizarOcupacaoServiceRemote {
	
	public List<facade.dto.Aula> visualizarOcupacao(String instalacao, String data) throws ApplicationException;
}

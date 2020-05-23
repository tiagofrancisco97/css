package controller.web.inputController.actions;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dto.Aula;
import facade.exceptions.ApplicationException;
import facade.handlers.IVisualizarOcupacaoServiceRemote;
import presentation.web.model.VisualizaOcupacaoModel;


/**
 * Handles the criar cliente event.
 * If the request is valid, it dispatches it to the domain model (the application's business logic)
 * Notice as well the use of an helper class to assist in the jsp response. 
 * 
 * @author fmartins
 *
 */
@Stateless
public class VisualizaOcupacaoAction extends Action {
	
	@EJB private IVisualizarOcupacaoServiceRemote visualizarOcupacaoHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		VisualizaOcupacaoModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			try {
				List <Aula> aulas =visualizarOcupacaoHandler.visualizarOcupacao(model.getInstalacao(), model.getData());
				//.addCustomer(intValue(model.getVATNumber()), 
						//model.getDesignation(), intValue(model.getPhoneNumber()), intValue(model.getDiscountType()));
				model.clearFields();
				model.addMessage("Aulas a decorrer na instalação: "+model.getInstalacao());
				
				for (Aula aula : aulas) {
					model.addMessage(aula.toString2());
					model.addMessage("-------------------------------------------------------");
				}
			} 
            catch (ApplicationException e) {
				model.addMessage("Error adding customer: " + e.getMessage());
			}
		} else {
			model.addMessage("Erro nos dados introduzidos");
		}
		
		request.getRequestDispatcher("/visualizaOcupacao/visualiza.jsp").forward(request, response);
	}

	
	private boolean validInput(VisualizaOcupacaoModel model) {
		
		// check if designation is filled
		boolean result = isFilled(model, model.getInstalacao(), "Deve colocar o nome da instalação");
		
		// check if VATNumber is filled and a valid number
		result &= isFilled(model, model.getData(), "Deve colocar uma data");
		
		// check in case phoneNumber is filled if it contains a valid number
		/*if (!model.getPhoneNumber().equals(""))
			result &= isInt(model, model.getPhoneNumber(), "Phone number contains invalid characters");

		// check if discount type is filled and a valid number
		result &= isFilled(model, model.getDiscountType(), "Discount type must be filled") 
					&& isInt(model, model.getDiscountType(), "Discount type with invalid characters");
		*/
		return result;
	}


	private VisualizaOcupacaoModel createHelper(HttpServletRequest request) {
		// Create the object model
		VisualizaOcupacaoModel model = new VisualizaOcupacaoModel();
		model.setAddCustomerHandler(visualizarOcupacaoHandler);

		// fill it with data from the request
		model.setInstalacao(request.getParameter("instalacao"));
		//model.setData(request.getParameter("data"));

		return model;
	}	
}

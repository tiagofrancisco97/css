package presentation.fx.inputcontroller;

import java.util.function.UnaryOperator;

import business.handlers.AulaHandler;
import facade.handlers.IAulaServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.CheckBox;
import presentation.fx.model.CriarAulaModel;

public class CriarAulaController extends BaseController {

	@FXML
	private TextField modalidadeTextField;

	@FXML
	private TextField nomeTextField;
	
	@FXML
	private TextField horaInicioTextField;
	
	@FXML
	private TextField duracaoTextField;

	@FXML
    private CheckBox segunda ;
	
	@FXML
    private CheckBox terca;
	
	@FXML
    private CheckBox quarta;
	
	@FXML
    private CheckBox quinta;
	
	@FXML
    private CheckBox sexta;
	
	@FXML
    private CheckBox sabado;
	
	@FXML
    private CheckBox domingo;

	private CriarAulaModel model;

	private IAulaServiceRemote aulaService;

	public void setCustomerService(IAulaServiceRemote aulaService) {
		this.aulaService = aulaService;
	}

	public void setModel(CriarAulaModel model) {
		this.model = model;
		modalidadeTextField.textProperty().bindBidirectional(model.designationProperty());
		nomeTextField.textProperty().bindBidirectional(model.vatNumberProperty(), new NumberStringConverter());
		horaInicioTextField.textProperty().bindBidirectional(model.phoneNumberProperty(), new NumberStringConverter());   	
		duracaoTextField.textProperty().bindBidirectional(model.vatNumberProperty(), new NumberStringConverter());
		segunda.selectedProperty().bindBidirectional(other);
		terca.selectedProperty().bindBidirectional(other);
		quarta.selectedProperty().bindBidirectional(other);
		quinta.selectedProperty().bindBidirectional(other);
		sexta.selectedProperty().bindBidirectional(other);
		sabado.selectedProperty().bindBidirectional(other);
		domingo.selectedProperty().bindBidirectional(other);
	}

	@FXML
	private void initialize() {
		
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) { 
				return change;
			}
			return null;
		};

		duracaoTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
	}

	@FXML
	void createCustomerAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				customerService.addCustomer(model.getVatNumber(), 
						model.getDesignation(), model.getPhoneNumber(), model.getSelectedDiscount().getDiscountCode()); 
				
				this.aulaService.criaAula(modalidade, nome, diasSemana, horaInicio, duracao);
				
				model.clearProperties();
				showInfo(i18nBundle.getString("new.customer.success"));
			} catch (VatInvalidException e) {
				showError(i18nBundle.getString("new.customer.error.vatInvalid") + ": " + e.getMessage());
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("new.customer.error.adding") + ": " + e.getMessage());
			}
		} else
			showError(i18nBundle.getString("new.customer.error.validating") + ":\n" + errorMessages);
			
	}

	private String validateInput() {	
		StringBuilder sb = new StringBuilder();
		String designation = model.getDesignation();
		if (designation == null || designation.length() == 0)
			sb.append(i18nBundle.getString("new.customer.invalid.designation"));
		if (model.getVatNumber() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.customer.invalid.VATNumber"));
		}
		if (model.getSelectedDiscount() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.customer.invalid.discount"));
		}

		return sb.toString();
	}

	@FXML
	void discountTypeSelected(ActionEvent event) {
		//model.setSelectedDiscount(discountTypeComboBox.getValue());
	}

}

package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.handlers.IAulaServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.AtivarAulaController;
import presentation.fx.model.AtivarAulaModel;



public class Startup extends Application {
    
	private static IAulaServiceRemote aulaService;

	@Override 
    public void start(Stage stage) throws IOException {
    
		// This line to resolve keys against Bundle.properties
		// ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
    	FXMLLoader ativarAulaLoader = new FXMLLoader(getClass().getResource("/fxml/newCustomer.fxml"), i18nBundle);
    	Parent root = ativarAulaLoader.load();
    	AtivarAulaController ativarAulaController = ativarAulaLoader.getController();    	
    	
    	AtivarAulaModel ativarAulaModel = new AtivarAulaModel(aulaService);
    	ativarAulaController.setModel(ativarAulaModel);
    	ativarAulaController.setCustomerService(aulaService);
    	ativarAulaController.setI18NBundle(i18nBundle);
    	
        Scene scene = new Scene(root, 450, 275);
       
        stage.setTitle(i18nBundle.getString("application.title"));
        stage.setScene(scene);
        stage.show();
    }
	
	public static void startGUI(IAulaServiceRemote aulaService) {
		Startup.aulaService = aulaService;
        launch();
	}
}

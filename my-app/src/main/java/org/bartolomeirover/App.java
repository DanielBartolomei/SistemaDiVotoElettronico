package org.bartolomeirover;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
	
	private static Scene scene;
	private static Controller controller;

	public static Controller getController() {
		return controller;
	}
	
	@Override
	public void start(final Stage stage) throws IOException {
		DbManager db = DbManager.getInstance();

		if (db.checkCreation()) {
        	System.out.println("OK");
        	//db.createFakeData();
        }else {
        	System.out.println("NOT OK");
        }
		
		/*LocalDate ld = LocalDate.of(2022, 9, 25); //LocalDate.now()
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		String fs = ld.format(formatter);
        System.out.println(fs);*/
		
		try {
			this.scene = new Scene(loadView("Home"));
	        stage.setScene(scene);
	        stage.setTitle("Votazioni elettroniche");
	        stage.sizeToScene();
	        stage.setResizable(false);
	        stage.show();
	        
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					event.consume();
					exit(stage);
				}
			});
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static Parent loadView(String view) throws IOException {
		FXMLLoader loader = new FXMLLoader(App.class.getResource("views/" + view + ".fxml"));
		Parent root = loader.load();
        return root;
    }

	
    public static Parent loadView(Controller sender, String view) throws IOException {
        return loadView(sender, view, null);
    }

    
    public static Parent loadView(Controller sender, String view, Object parameter) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("views/" + view + ".fxml"));
        Parent parent = loader.load();
        controller = loader.getController();
        controller.onNavigateFrom(sender, parameter);
        controller.init();
        return parent;
    }

    
    public static void navigate(Controller sender, String view, Object parameter) throws IOException {
        Parent parent = loadView(sender, view, parameter);
        scene.setRoot(parent);
        scene.getWindow().sizeToScene();
    }

    
    public static void navigate(Controller sender, String view) throws IOException {
        navigate(sender, view, null);
    }
    
    public void exit(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Chiusura programma");
		alert.setHeaderText("Stai per chiudere il programma di votazione");
		alert.setContentText("Sei sicuro?");
		
		if (alert.showAndWait().get() == ButtonType.OK) {
			System.out.println("You successfully exited");
			stage.close();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

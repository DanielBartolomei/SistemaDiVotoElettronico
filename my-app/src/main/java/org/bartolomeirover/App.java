package org.bartolomeirover;

import java.io.IOException;
import java.net.URL;

import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
	
	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException {
		DbManager db = DbManager.getInstance();

		if (db.checkCreation()) {
        	System.out.println("OK");
        	//db.createFakeData();
        }else {
        	System.out.println("NOT OK");
        }
		
		try {
			this.scene = new Scene(loadView("Home"));
	        stage.setScene(scene);
	        stage.setTitle("Votazioni elettroniche");
	        stage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Parent loadView(String view) throws IOException {
		FXMLLoader loader = new FXMLLoader(App.class.getResource("views/" + view + ".fxml"));
		Parent root = loader.load();
        System.out.println("View" + view + "loaded");
        return root;
    }

    public static Parent loadView(Controller sender, String view) throws IOException {
        return loadView(sender, view, null);
    }

    public static Parent loadView(Controller sender, String view, Object parameter) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("views/" + view + ".fxml"));
        Parent parent = loader.load();
        Controller controller = loader.getController();
        controller.onNavigateFrom(sender, parameter);
        controller.init();
        return parent;
    }

    public static void navigate(Controller sender, String view, Object parameter) throws IOException {
        Parent parent = loadView(sender, view, parameter);
        scene.setRoot(parent);
    }

    public static void navigate(Controller sender, String view) throws IOException {
        navigate(sender, view, null);
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}

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
import javafx.stage.Stage;

public class App extends Application {
	
	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException{
		DbManager db = DbManager.getInstance();

		if (db.checkCreation()) {
        	System.out.println("OK");
        	//db.createFakeData();
        }else {
        	System.out.println("NOT OK");
        }
		
		/*scene = new Scene(loadView("Login"), 1280, 720);
		System.out.println("postLoadView");
        navigate(null, "Login");
        System.out.println("postLoadView");
        stage.setScene(scene);
        stage.setTitle("Bike Sharing");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();*/
	}
	
	public static Parent loadView(String view) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		final URL resource = App.class.getResource("views/" + view + ".fxml");
		loader.setLocation(resource);
        System.out.println("OK");
        return loader.load();
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

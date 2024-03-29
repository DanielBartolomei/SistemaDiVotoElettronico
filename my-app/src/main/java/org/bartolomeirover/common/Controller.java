package org.bartolomeirover.common;

import java.io.IOException;

import org.bartolomeirover.App;


public abstract class Controller {
	public void init() {

    }

    public void onNavigateFrom(Controller sender, Object parameter) {

    }

    public void navigate(String view, Object parameter) {
        try {
            App.navigate(this, view, parameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigate(String view) {
        navigate(view, null);
    }
}

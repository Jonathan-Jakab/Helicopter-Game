package org.csc133.a5;

import com.codename1.io.Log;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import static com.codename1.ui.CN.*;
import static com.codename1.ui.CN.getCurrentForm;


public class AppMain {

    private Form current;
    private Resources theme;

    public void init(Object context) {

        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        Toolbar.setGlobalToolbar(true);

        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {

            err.consume();
            if (err.getError() != null) {

                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        new Game();
    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {}
}
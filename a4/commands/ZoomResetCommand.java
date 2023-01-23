package org.csc133.a4.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a4.views.MapView;

public class ZoomResetCommand extends Command {

    private MapView myMapView;

    public ZoomResetCommand(MapView mapView) {

        super("ZoomReset");
        this.myMapView = mapView;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Mapview Reset.");
        myMapView.mapviewResetZoom();
    }
}

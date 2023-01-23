package org.csc133.a4.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a4.views.MapView;

public class ZoomInCommand extends Command {

    private MapView myMapView;

    public ZoomInCommand(MapView mapView) {

        super("ZoomIn");
        this.myMapView = mapView;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Mapview Zoomed.");
        myMapView.mapviewZoomIn();
    }
}
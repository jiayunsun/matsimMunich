package org.matsim.munichArea.outputCreation.tripDurationAnalyzer;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.*;
import org.matsim.api.core.v01.events.handler.*;
import org.matsim.munichArea.configMatsim.createDemand.PtSyntheticTraveller;

import java.util.Map;

/**
 * Created by carlloga on 17.03.2017.
 */

public class ActivityStartEndHandler implements ActivityEndEventHandler,
        ActivityStartEventHandler, PersonDepartureEventHandler, PersonArrivalEventHandler, PersonEntersVehicleEventHandler {



    private Map<Id, Trip> tripMap;


    public ActivityStartEndHandler(Map<Id, Trip> tripMap) {
        this.tripMap = tripMap;

    }

    @Override
    public void reset(int iteration) {
    }


    @Override
    public void handleEvent(ActivityEndEvent event) {
        //detects end of activity home
        if (event.getActType().equals("home")){
            Trip t = new Trip(event.getPersonId());
            tripMap.put(event.getPersonId(), t);
        }

    }

    public void handleEvent(PersonDepartureEvent event) {
        //detects the event of departing from home and assigns departure time and mode
        try {
        Trip t = tripMap.get(event.getPersonId());
        //only if not yet at work
        if (!t.isAtWorkPlace()) {
            t.setDepartureTime(event.getTime());
            t.setMode(event.getLegMode().toString());
        }
        }catch (Exception e){

        }
    }

    @Override
    public void handleEvent (PersonEntersVehicleEvent event){

        try {
            Trip t = tripMap.get(event.getPersonId());
            //only if not yet at work
            if (!t.isAtWorkPlace()) {
                t.setVehicleStartTime(event.getTime());
            }
        }catch (Exception e){

        }
        //}
    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {
        //detects the event of arriving to work
        try {
        Trip t = tripMap.get(event.getPersonId());
        //only if not yet at work
        if (!t.isAtWorkPlace()) {
            t.setArrivalTime(event.getTime());
        }
        }catch (Exception e){

        }
    }

    @Override
    public void handleEvent(ActivityStartEvent event) {
        //detects the event of arriving to work
        try {
        if (event.getActType().equals("work")){
            Trip t = tripMap.get(event.getPersonId());
            t.setAtWorkPlace(true);
        }
        }catch (Exception e){

        }

    }



}


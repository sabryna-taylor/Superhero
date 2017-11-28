/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.service;

import com.sg.superhero_security.dao.LocationDao;
import com.sg.superhero_security.model.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public class LocationsServiceImpl implements LocationsService {

    Location l;
    LocationDao dao;

    public LocationsServiceImpl(LocationDao dao) {
        this.dao = dao;
    }

    @Override
    public void addLocation(Location l) {
        dao.addLocation(l);
        this.l = l;
    }

    @Override
    public void deleteLocation(int lId) {
        dao.deleteLocation(lId);
    }

    @Override
    public void updateLocation(Location l) {
        dao.updateLocation(l);
        this.l = l;
    }

    @Override
    public Location getLocationById(int lId) {
        l = dao.getLocationById(lId);
        return l;
    }

    @Override
    public List<Location> getAllLocations() {
        return new ArrayList<>(dao.getAllLocations());
    }

}

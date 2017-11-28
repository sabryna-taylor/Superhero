/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.service;

import com.sg.superhero_security.model.Location;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public interface LocationsService {

    public void addLocation(Location l);

    public void deleteLocation(int lId);

    public void updateLocation(Location l);

    public Location getLocationById(int lId);

    public List<Location> getAllLocations();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Location;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public interface LocationDao {

    public void addLocation(Location l);

    public void deleteLocation(int locationId);

    public void updateLocation(Location l);

    public Location getLocationById(int locationId);

    public List<Location> getAllLocations();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.service;

import com.sg.superhero_security.model.Sighting;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public interface SightingsService {

    public void addSighting(Sighting s);

    public void deleteSighting(int sId);

    public void updateSighting(Sighting s);

    public Sighting getSightingById(int sId);

    public List<Sighting> getAllSightings();

    public List<Sighting> getAllSightingsOrderByNameDate();

}

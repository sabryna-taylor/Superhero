/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.service;

import com.sg.superhero_security.dao.SightingDao;
import com.sg.superhero_security.model.Sighting;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public class SightingsServiceImpl implements SightingsService {

    Sighting newSighting;
    SightingDao dao;

    public SightingsServiceImpl(SightingDao dao) {
        this.dao = dao;
    }

    @Override
    public void addSighting(Sighting s) {
        dao.addSighting(s);
        newSighting = s;
    }

    @Override
    public void deleteSighting(int sId) {
        dao.deleteSighting(sId);
    }

    @Override
    public void updateSighting(Sighting s) {
        dao.updateSighting(s);
        newSighting = s;
    }

    @Override
    public Sighting getSightingById(int sId) {
        newSighting = dao.getSightingById(sId);
        return newSighting;
    }

    @Override
    public List<Sighting> getAllSightings() {
        return new ArrayList<>(dao.getAllSightings());
    }

    @Override
    public List<Sighting> getAllSightingsOrderByNameDate() {
        return new ArrayList<>(dao.getAllSightingsOrderByNameDate());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Superperson;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public interface SuperpersonDao {

    public void addSuperperson(Superperson sp);

    public void deleteSuperperson(int superpersonId);

    public void updateSuperperson(Superperson sp);

    public Superperson getSuperpersonById(int superpersonId);

    public List<Superperson> getAllSuperpersons();

    public List<Superperson> getSuperpersonByOrganization(int organizationId);
    

}

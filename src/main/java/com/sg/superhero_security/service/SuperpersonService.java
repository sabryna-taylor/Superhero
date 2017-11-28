/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.service;

import com.sg.superhero_security.controller.ErrorMessage;
import com.sg.superhero_security.model.Organization;
import com.sg.superhero_security.model.Superperson;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public interface SuperpersonService {

    public void addSuperperson(Superperson sp);

    public void deleteSuperperson(int superpersonId);

    public void updateSuperperson(Superperson sp);

    public Superperson getSuperpersonById(int spId);

    public List<Superperson> getAllSuperpersons();

    public List<Superperson> getSuperpersonByOrganization(int organizationId);

    public List<Organization> getOrganizationsBySuperperson();
}

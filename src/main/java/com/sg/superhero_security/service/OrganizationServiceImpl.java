/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.service;

import com.sg.superhero_security.controller.ErrorMessage;
import com.sg.superhero_security.dao.OrganizationDao;
import com.sg.superhero_security.model.Organization;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public class OrganizationServiceImpl implements OrganizationService {

    Organization o;
    OrganizationDao dao;

    public OrganizationServiceImpl(OrganizationDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrganization(Organization o) {
        dao.addOrganization(o);
        this.o = o;
    }

    @Override
    public void deleteOrganization(int organizationId) {
        dao.deleteOrganization(organizationId);
    }

    @Override
    public void updateOrganization(Organization o) {
        dao.updateOrganization(o);
        this.o = o;
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        o = dao.getOrganizationById(organizationId);
        return o;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return new ArrayList<>(dao.getAllOrganizations());
    }

}

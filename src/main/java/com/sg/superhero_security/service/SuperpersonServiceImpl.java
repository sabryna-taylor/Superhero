/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.service;

import com.sg.superhero_security.controller.ErrorMessage;
import com.sg.superhero_security.model.Superperson;
import com.sg.superhero_security.dao.SuperpersonDao;
import com.sg.superhero_security.model.Organization;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public class SuperpersonServiceImpl implements SuperpersonService {

    Superperson sp;
    SuperpersonDao dao;
    int spId;

    public SuperpersonServiceImpl(SuperpersonDao dao) {
        this.dao = dao;
    }

    @Override
    public void addSuperperson(Superperson sp) {
        this.sp = sp;
        dao.addSuperperson(sp);
    }

    @Override
    public void deleteSuperperson(int superpersonId) {
        dao.deleteSuperperson(superpersonId);
    }

    @Override
    public void updateSuperperson(Superperson sp) {
        dao.updateSuperperson(sp);
    }

    @Override
    public Superperson getSuperpersonById(int spId) {
        sp = dao.getSuperpersonById(spId);
        this.spId = spId;
        return sp;
    }

    @Override
    public List<Superperson> getAllSuperpersons() {
        return new ArrayList<>(dao.getAllSuperpersons());
    }

    @Override
    public List<Superperson> getSuperpersonByOrganization(int organizationId) {
        return new ArrayList<>(dao.getSuperpersonByOrganization(organizationId));
    }

    @Override
    public List<Organization> getOrganizationsBySuperperson() {
        return new ArrayList<>(sp.getOrganizations());
    }
}

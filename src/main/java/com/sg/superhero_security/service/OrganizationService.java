/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.service;

import com.sg.superhero_security.model.Organization;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public interface OrganizationService {

    public void addOrganization(Organization o);

    public void deleteOrganization(int organizationId);

    public void updateOrganization(Organization o);

    public Organization getOrganizationById(int organizationId);

    public List<Organization> getAllOrganizations();

}

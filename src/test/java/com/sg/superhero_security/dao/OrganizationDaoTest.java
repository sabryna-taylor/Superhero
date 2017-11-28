/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Organization;
import com.sg.superhero_security.model.Superperson;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Sabryna
 */
public class OrganizationDaoTest {

    OrganizationDao oDao;
    SuperpersonDao spDao;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        oDao = ctx.getBean("oDao", OrganizationDao.class);

        spDao = ctx.getBean("spDao", SuperpersonDao.class);

        List<Superperson> superpersons = spDao.getAllSuperpersons();
        for (Superperson currentSuperperson : superpersons) {
            spDao.deleteSuperperson(currentSuperperson.getSuperpersonId());
        }

        List<Organization> organizations = oDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            oDao.deleteOrganization(currentOrganization.getOrganizationId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addOrganization method, of class OrganizationDao.
     */
    @Test
    public void testAddGetOrganization() {
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("The Avengers. It's what we call ourselves, "
                + "sort of like a team. 'Earth's Mightiest Heroes' type of thing.");
        o.setAddress("890 Fifth Avenue, Manhattan, New York City");
        o.setContactInfo("212-576-4000");
        oDao.addOrganization(o);

        Organization fromDao = oDao.getOrganizationById(o.getOrganizationId());
        assertEquals(fromDao, o);
    }

    /**
     * Test of deleteOrganization method, of class OrganizationDao.
     */
    @Test
    public void testDeleteOrganization() {
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("The Avengers. It's what we call ourselves, "
                + "sort of like a team. 'Earth's Mightiest Heroes' type of thing.");
        o.setAddress("890 Fifth Avenue, Manhattan, New York City");
        o.setContactInfo("212-576-4000");
        oDao.addOrganization(o);

        Organization fromDao = oDao.getOrganizationById(o.getOrganizationId());
        assertEquals(fromDao, o);
        oDao.deleteOrganization(o.getOrganizationId());
        assertNull(oDao.getOrganizationById(o.getOrganizationId()));
    }

    /**
     * Test of updateOrganization method, of class OrganizationDao.
     */
    @Test
    public void testUpdateOrganization() {
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("The Avengers. It's what we call ourselves, "
                + "sort of like a team. 'Earth's Mightiest Heroes' type of thing.");
        o.setAddress("890 Fifth Avenue, Manhattan, New York City");
        o.setContactInfo("212-576-4000");
        oDao.addOrganization(o);

        o.setName("Avengers 2.0");
        oDao.updateOrganization(o);

        Organization fromDao = oDao.getOrganizationById(o.getOrganizationId());
        assertEquals(fromDao, o);

    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDao.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("The Avengers. It's what we call ourselves, "
                + "sort of like a team. 'Earth's Mightiest Heroes' type of thing.");
        o.setAddress("890 Fifth Avenue, Manhattan, New York City");
        o.setContactInfo("212-576-4000");
        oDao.addOrganization(o);

        assertEquals(1, oDao.getAllOrganizations().size());
    }

}

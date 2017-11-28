/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Organization;
import com.sg.superhero_security.model.Sighting;
import com.sg.superhero_security.model.Superperson;
import java.util.ArrayList;
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
public class SuperpersonDaoTest {

    SuperpersonDao spDao;
    OrganizationDao oDao;
    SightingDao sDao;

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
        spDao = ctx.getBean("spDao", SuperpersonDao.class);

        sDao = ctx.getBean("sDao", SightingDao.class);
        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sDao.deleteSighting(currentSighting.getSightingId());
        }

        List<Superperson> superpersons = spDao.getAllSuperpersons();
        for (Superperson currentSuperperson : superpersons) {
            spDao.deleteSuperperson(currentSuperperson.getSuperpersonId());
        }

        oDao = ctx.getBean("oDao", OrganizationDao.class);
        List<Organization> organizations = oDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            oDao.deleteOrganization(currentOrganization.getOrganizationId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSuperperson method, of class SuperpersonDao.
     */
    @Test
    public void testAddGetSuperperson() {
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("The Avengers. It's what we call ourselves, "
                + "sort of like a team. 'Earth's Mightiest Heroes' type of thing.");
        o.setAddress("890 Fifth Avenue, Manhattan, New York City");
        o.setContactInfo("212-576-4000");
        oDao.addOrganization(o);

        Superperson sp = new Superperson();
        sp.setName("Spider-Man");
        sp.setPower("Superhuman strength, reflexes, and balance. Ability to "
                + "cling to majority of surfaces. Subconscious ability to sense "
                + "everything in his surroundings, AKA- 'spidey-sense'.");
        sp.setDescription("");

        List<Organization> organizations = new ArrayList<>();
        organizations.add(o);
        sp.setOrganizations(organizations);

        spDao.addSuperperson(sp);

        Superperson fromDao = spDao.getSuperpersonById(sp.getSuperpersonId());

        assertEquals(fromDao, sp);
    }

    /**
     * Test of deleteSuperperson method, of class SuperpersonDao.
     */
    @Test
    public void testDeleteSuperperson() {

        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("The Avengers. It's what we call ourselves, "
                + "sort of like a team. 'Earth's Mightiest Heroes' type of thing.");
        o.setAddress("890 Fifth Avenue, Manhattan, New York City");
        o.setContactInfo("212-576-4000");
        oDao.addOrganization(o);

        Superperson sp = new Superperson();
        sp.setName("Spider-Man");
        sp.setPower("Superhuman strength, reflexes, and balance. Ability to "
                + "cling to majority of surfaces. Subconscious ability to sense "
                + "everything in his surroundings, AKA- 'spidey-sense'.");
        sp.setDescription("");

        List<Organization> organizations = new ArrayList<>();
        organizations.add(o);
        sp.setOrganizations(organizations);

        spDao.addSuperperson(sp);

        Superperson fromDao = spDao.getSuperpersonById(sp.getSuperpersonId());

        assertEquals(fromDao, sp);
        spDao.deleteSuperperson(sp.getSuperpersonId());
        assertNull(spDao.getSuperpersonById(sp.getSuperpersonId()));
    }

    /**
     * Test of updateSuperperson method, of class SuperpersonDao.
     */
    @Test
    public void testUpdateSuperperson() {

        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("The Avengers. It's what we call ourselves, "
                + "sort of like a team. 'Earth's Mightiest Heroes' type of thing.");
        o.setAddress("890 Fifth Avenue, Manhattan, New York City");
        o.setContactInfo("212-576-4000");
        oDao.addOrganization(o);

        Superperson sp = new Superperson();
        sp.setName("Spider-Man");
        sp.setPower("Superhuman strength, reflexes, and balance. Ability to "
                + "cling to majority of surfaces. Subconscious ability to sense "
                + "everything in his surroundings, AKA- 'spidey-sense'.");
        sp.setDescription("");

        List<Organization> organizations = new ArrayList<>();
        organizations.add(o);
        sp.setOrganizations(organizations);

        spDao.addSuperperson(sp);

        sp.setDescription("H-h-h-hi I'm Peter.");
        spDao.updateSuperperson(sp);

        Superperson fromDao = spDao.getSuperpersonById(sp.getSuperpersonId());

        assertEquals(fromDao, sp);
    }

    /**
     * Test of getAllSuperpersons method, of class SuperpersonDao.
     */
    @Test
    public void testGetAllSuperpersons() {

        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("The Avengers. It's what we call ourselves, "
                + "sort of like a team. 'Earth's Mightiest Heroes' type of thing.");
        o.setAddress("890 Fifth Avenue, Manhattan, New York City");
        o.setContactInfo("212-576-4000");
        oDao.addOrganization(o);

        Superperson sp = new Superperson();
        sp.setName("Spider-Man");
        sp.setPower("Superhuman strength, reflexes, and balance. Ability to "
                + "cling to majority of surfaces. Subconscious ability to sense "
                + "everything in his surroundings, AKA- 'spidey-sense'.");
        sp.setDescription("");

        List<Organization> organizations = new ArrayList<>();
        organizations.add(o);
        sp.setOrganizations(organizations);

        spDao.addSuperperson(sp);

        assertEquals(1, spDao.getAllSuperpersons().size());
    }

}

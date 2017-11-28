/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;


import com.sg.superhero_security.model.Location;
import com.sg.superhero_security.model.Organization;
import com.sg.superhero_security.model.Sighting;
import com.sg.superhero_security.model.Superperson;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class SightingDaoTest {

    SightingDao sDao;
    LocationDao lDao;
    SuperpersonDao spDao;
    OrganizationDao oDao;

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

        sDao = ctx.getBean("sDao", SightingDao.class);
        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sDao.deleteSighting(currentSighting.getSightingId());
        }

        spDao = ctx.getBean("spDao", SuperpersonDao.class);
        List<Superperson> superpersons = spDao.getAllSuperpersons();
        for (Superperson currentSuperperson : superpersons) {
            spDao.deleteSuperperson(currentSuperperson.getSuperpersonId());
        }

        oDao = ctx.getBean("oDao", OrganizationDao.class);
        List<Organization> organizations = oDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            oDao.deleteOrganization(currentOrganization.getOrganizationId());
        }
        lDao = ctx.getBean("lDao", LocationDao.class);
        List<Location> locations = lDao.getAllLocations();
        for (Location currentLocation : locations) {
            lDao.deleteLocation(currentLocation.getLocationId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSighting method, of class SightingDao.
     */
    @Test
    public void testAddGetSighting() {
        Location l = new Location();
        l.setNameOfResidence("Midtown School of Science and Technology");
        l.setAddress("Forest Hills");
        l.setLatitude(BigDecimal.ONE);
        l.setLongitude(BigDecimal.ONE);
        l.setDescription("I got... homework");
        lDao.addLocation(l);

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
        sp.setDescription("A shy kid");
        List<Organization> organizations = new ArrayList();
        organizations.add(o);
        sp.setOrganizations(organizations);
        spDao.addSuperperson(sp);

        Sighting sighting = new Sighting();
        sighting.setDescription("It's a bird... It's a plane... It's Superman!");
        sighting.setDateSeen(LocalDate.now());
        sighting.setSp(sp);
        sighting.setLocation(l);
        sighting.setFileName("ironman");
        sighting.setTitle("IRON MAN");
        sDao.addSighting(sighting);

        Sighting fromDao = sDao.getSightingById(sighting.getSightingId());
        assertEquals(fromDao.getSightingId(), sighting.getSightingId());
    }

    /**
     * Test of deleteSighting method, of class SightingDao.
     */
    @Test
    public void testDeleteSighting() {
        Location l = new Location();
        l.setNameOfResidence("Midtown School of Science and Technology");
        l.setAddress("Forest Hills");
        l.setLatitude(BigDecimal.ONE);
        l.setLongitude(BigDecimal.ONE);
        l.setDescription("I got... homework");
        lDao.addLocation(l);

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
        sp.setDescription("A shy kid");
        List<Organization> organizations = new ArrayList();
        organizations.add(o);
        sp.setOrganizations(organizations);
        spDao.addSuperperson(sp);

        Sighting sighting = new Sighting();
        sighting.setDescription("It's a bird... It's a plane... It's Superman!");
        sighting.setDateSeen(LocalDate.now());
        sighting.setSp(sp);
        sighting.setLocation(l);
        sighting.setFileName("ironman");
        sighting.setTitle("IRON MAN");
        sDao.addSighting(sighting);

        Sighting fromDao = sDao.getSightingById(sighting.getSightingId());
        assertEquals(fromDao.getSightingId(), sighting.getSightingId());
        sDao.deleteSighting(sighting.getSightingId());
        assertNull(sDao.getSightingById(sighting.getSightingId()));
    }

    /**
     * Test of getAllSightings method, of class SightingDao.
     */
    @Test
    public void testGetAllSightings() {
        Location l = new Location();
        l.setNameOfResidence(" Midtown School of Science and Technology");
        l.setAddress("Forest Hills");
        l.setLatitude(BigDecimal.ONE);
        l.setLongitude(BigDecimal.ONE);
        l.setDescription("I got... homework ");
        lDao.addLocation(l);

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
        sp.setDescription("A shy kid");
        List<Organization> organizations = new ArrayList();
        organizations.add(o);
        sp.setOrganizations(organizations);
        spDao.addSuperperson(sp);

        Sighting s = new Sighting();
        s.setDescription("It's a bird... It's a plane... It's Superman!");
        s.setDateSeen(LocalDate.now());
        s.setLocation(l);
        s.setSp(sp);
        s.setFileName("ironman");
        s.setTitle("IRON MAN");
        sDao.addSighting(s);

        assertEquals(1, sDao.getAllSightings().size());
    }

}

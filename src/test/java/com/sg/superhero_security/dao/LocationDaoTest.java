/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Location;
import com.sg.superhero_security.model.Sighting;
import java.math.BigDecimal;
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
public class LocationDaoTest {

    LocationDao lDao;
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
        lDao = ctx.getBean("lDao", LocationDao.class);
        sDao = ctx.getBean("sDao", SightingDao.class);

        sDao = ctx.getBean("sDao", SightingDao.class);
        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sDao.deleteSighting(currentSighting.getSightingId());
        }

        List<Location> locations = lDao.getAllLocations();
        for (Location currentLocation : locations) {
            lDao.deleteLocation(currentLocation.getLocationId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addLocation method, of class LocationDao.
     */
    @Test
    public void testAddGetLocation() {
        Location l = new Location();
        l.setNameOfResidence("Batcave");
        l.setAddress("1111 Main St, Gotham");
        l.setLatitude(BigDecimal.ONE);
        l.setLongitude(BigDecimal.ONE);
        l.setDescription("Batman has no limits");
        lDao.addLocation(l);

        Location fromDao = lDao.getLocationById(l.getLocationId());
        assertEquals(fromDao.getLocationId(), l.getLocationId());
    }

    /**
     * Test of deleteLocation method, of class LocationDao.
     */
    @Test
    public void testDeleteLocation() {
        Location l = new Location();
        l.setNameOfResidence("Batcave");
        l.setAddress("1111 Main St, Gotham");
        l.setLatitude(BigDecimal.ONE);
        l.setLongitude(BigDecimal.ONE);
        l.setDescription("Batman has no limits");
        lDao.addLocation(l);

        Location fromDao = lDao.getLocationById(l.getLocationId());
        assertEquals(fromDao.getLocationId(), l.getLocationId());
        lDao.deleteLocation(l.getLocationId());
        assertNull(lDao.getLocationById(l.getLocationId()));
    }

    /**
     * Test of updateLocation method, of class LocationDao.
     */
    @Test
    public void testUpdateLocation() {
        Location l = new Location();
        l.setNameOfResidence("Batcave");
        l.setAddress("1111 Main St, Gotham");
        l.setLatitude(BigDecimal.ONE);
        l.setLongitude(BigDecimal.ONE);
        l.setDescription("Batman has no limits");
        lDao.addLocation(l);

        l.setNameOfResidence("THE Batcave");
        lDao.updateLocation(l);

        Location fromDao = lDao.getLocationById(l.getLocationId());
        assertEquals(fromDao.getLocationId(), l.getLocationId());
    }

    /**
     * Test of getAllLocations method, of class LocationDao.
     */
    @Test
    public void testGetAllLocations() {
        Location l = new Location();
        l.setNameOfResidence("Batcave");
        l.setAddress("1111 Main St, Gotham");
        l.setLatitude(BigDecimal.ONE);
        l.setLongitude(BigDecimal.ONE);
        l.setDescription("Batman has no limits");
        lDao.addLocation(l);

        assertEquals(1, lDao.getAllLocations().size());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sabryna
 */
public class LocationDaoDbImpl implements LocationDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //location
    private static final String SQL_INSERT_LOCATION
            = "insert into Location (`NameOfResidence`, Address, Latitude, Longitude, "
            + "Description) values (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_LOCATION
            = "delete from Location where LocationId = ?";

    private static final String SQL_UPDATE_LOCATION
            = "update Location set `NameOfResidence` = ?, Address = ?, Latitude = ?, "
            + "Longitude = ?, Description = ? where LocationId =  ?";

    private static final String SQL_SELECT_LOCATION
            = "select * from Location where LocationId = ?";

    private static final String SQL_SELECT_ALL_LOCATIONS
            = "select * from Location";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location l) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                l.getNameOfResidence(),
                l.getAddress(),
                l.getLatitude(),
                l.getLongitude(),
                l.getDescription());

        int locationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        l.setLocationId(locationId);

    }

    @Override
    public void deleteLocation(int locationId) {
        jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
    }

    @Override
    public void updateLocation(Location l) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                l.getNameOfResidence(),
                l.getAddress(),
                l.getLatitude(),
                l.getLongitude(),
                l.getDescription(),
                l.getLocationId());
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION,
                    new LocationMapper(),
                    locationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location l = new Location();
            l.setLocationId(rs.getInt("LocationId"));
            l.setNameOfResidence(rs.getString("NameOfResidence"));
            l.setAddress(rs.getString("Address"));
            l.setLatitude(rs.getBigDecimal("Latitude"));
            l.setLongitude(rs.getBigDecimal("Longitude"));
            l.setDescription(rs.getString("Description"));
            return l;
        }
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Location;
import com.sg.superhero_security.model.Sighting;
import com.sg.superhero_security.model.Superperson;
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
public class SightingDaoDbImpl implements SightingDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //sighting
    private static final String SQL_INSERT_SIGHTING
            = "insert into Sighting (DateSeen, Description, SuperpersonId, LocationId, FileName, Title) "
            + "values (?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_SIGHTING
            = "delete from Sighting where SightingId = ?";

    private static final String SQL_UPDATE_SIGHTING
            = "update Sighting set DateSeen = ?, Description = ?, SuperpersonId = ?, "
            + "LocationId = ?, FileName = ?, Title = ? where SightingId =  ?";

    private static final String SQL_SELECT_SIGHTING
            = "select * from Sighting where SightingId = ?";

    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "select * from Sighting";

    private static final String SQL_SELECT_ALL_SIGHTINGS_ORDER_BY_NAME_DATE
            = "select `Name`, Power, Sighting.Description, DateSeen, FileName, Title,"
            + " SightingId from Superperson "
            + " inner join Sighting on Superperson.SuperpersonId = Sighting.SuperpersonId "
            + " order by `Name`,  DateSeen desc limit 0,10;";

    private static final String SQL_SELECT_LOCATION_BY_SIGHTING_ID
            = "select l.LocationId, l.NameOfResidence, l.Address, l.Latitude, "
            + " l.Longitude, l.Description from Location l "
            + "join Sighting on l.LocationId = Sighting.LocationId where "
            + "Sighting.SightingId = ?";

    private static final String SQL_SELECT_SUPERPERSON_BY_SIGHTING_ID
            = "select sp.SuperpersonId, sp.Name, sp.Power, sp.Description "
            + " from Superperson sp join Sighting on sp.SuperpersonId = Sighting.SuperpersonId where "
            + " Sighting.SightingId = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting s) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                s.getDateSeen().toString(),
                s.getDescription(),
                s.getSp().getSuperpersonId(),
                s.getLocation().getLocationId(),
                s.getFileName(),
                s.getTitle());
        s.setSightingId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

    }

    @Override
    public void deleteSighting(int sightingId) {
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingId);

    }

    @Override
    public void updateSighting(Sighting s) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                s.getDateSeen().toString(),
                s.getDescription(),
                s.getSp().getSuperpersonId(),
                s.getLocation().getLocationId(),
                s.getFileName(),
                s.getTitle(),
                s.getSightingId());
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        try {

            Sighting sighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING,
                    new SightingMapper(),
                    sightingId);

            sighting.setSp(findSuperpersonForSighting(sighting));
            sighting.setLocation(findLocationForSighting(sighting));

            return sighting;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sList = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, new SightingMapper());
        return associateLocationSuperpersonWithSighting(sList);
    }

    @Override
    public List<Sighting> getAllSightingsOrderByNameDate() {
        List<Sighting> sList = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_ORDER_BY_NAME_DATE, new SightingMapper());
        return associateLocationSuperpersonWithSighting(sList);
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting s = new Sighting();
            s.setSightingId(rs.getInt("SightingId"));
            s.setDateSeen(rs.getTimestamp("DateSeen").toLocalDateTime().toLocalDate());
            s.setDescription(rs.getString("Description"));
            s.setFileName(rs.getString("FileName"));
            s.setTitle(rs.getString("Title"));
            return s;
        }
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location l = new Location();
            l.setNameOfResidence(rs.getString("NameOfResidence"));
            l.setAddress(rs.getString("Address"));
            l.setLatitude(rs.getBigDecimal("Latitude"));
            l.setLongitude(rs.getBigDecimal("Longitude"));
            l.setDescription(rs.getString("Description"));
            l.setLocationId(rs.getInt("LocationId"));
            return l;
        }
    }

    private static final class SuperpersonMapper implements RowMapper<Superperson> {

        @Override
        public Superperson mapRow(ResultSet rs, int i) throws SQLException {
            Superperson sp = new Superperson();
            sp.setSuperpersonId(rs.getInt("SuperpersonId"));
            sp.setName(rs.getString("Name"));
            sp.setPower(rs.getString("Power"));
            sp.setDescription(rs.getString("Description"));
            return sp;
        }
    }

    private Location findLocationForSighting(Sighting s) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTING_ID,
                new LocationMapper(),
                s.getSightingId());
    }

    private Superperson findSuperpersonForSighting(Sighting s) {
        return jdbcTemplate.queryForObject(SQL_SELECT_SUPERPERSON_BY_SIGHTING_ID,
                new SuperpersonMapper(),
                s.getSightingId());
    }

    private List<Sighting>
            associateLocationSuperpersonWithSighting(List<Sighting> sList) {
        for (Sighting currentSighting : sList) {

            currentSighting.setLocation(findLocationForSighting(currentSighting));
            currentSighting.setSp(findSuperpersonForSighting(currentSighting));

        }
        return sList;
    }

}

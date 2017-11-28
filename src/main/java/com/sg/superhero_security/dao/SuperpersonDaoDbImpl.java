/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Organization;
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
public class SuperpersonDaoDbImpl implements SuperpersonDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_SELECT_ORGANIZATIONS_BY_SUPERPERSON_ID
            = "select o.OrganizationId, o.Name, o.Description, o.Address, o.ContactInfo"
            + " from Organization o join Superperson_Organization spo on o.OrganizationId = spo.OrganizationId where "
            + "spo.SuperpersonId = ?";

    //superperson, superperson_organization
    private static final String SQL_INSERT_SUPERPERSON
            = "insert into Superperson (`Name`, Power, Description) values (?, ?, ?)";

    private static final String SQL_INSERT_SUPERPERSON_ORGANIZATION
            = "insert into Superperson_Organization (SuperpersonId, OrganizationId) values (?, ?)";

    private static final String SQL_DELETE_SUPERPERSON
            = "delete from Superperson where SuperpersonId = ?";
    private static final String SQL_DELETE_SUPERPERSON_ORGANIZATION
            = "delete from Superperson_Organization where SuperpersonId = ?";

    private static final String SQL_UPDATE_SUPERPERSON
            = "update Superperson set `Name` = ?, Power = ?, Description = ? where SuperpersonId =  ?";

    private static final String SQL_SELECT_SUPERPERSON
            = "select * from Superperson where SuperpersonId = ?";

    private static final String SQL_SELECT_ALL_SUPERPERSONS
            = "select * from Superperson";

    private static final String SQL_SELECT_SUPERPERSONS_BY_ORGANIZATION_ID
            = " select s.SuperpersonId, s.Name, s.Power, s.Description from Superperson s "
            + "join Superperson_Organization spo on OrganizationId where s.SuperpersonId = spo.SuperpersonId "
            + "and spo.OrganizationId = ?;";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperperson(Superperson sp) {
        // first insert into superpersons table and get newly generated id
        jdbcTemplate.update(SQL_INSERT_SUPERPERSON,
                sp.getName(),
                sp.getPower(),
                sp.getDescription());
        sp.setSuperpersonId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

        // now update the bridge table
        insertSuperpersonOrganization(sp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuperperson(int superpersonId) {
        // delete bridge table relationship for this superperson
        jdbcTemplate.update(SQL_DELETE_SUPERPERSON_ORGANIZATION, superpersonId);

        // then delete superperson
        jdbcTemplate.update(SQL_DELETE_SUPERPERSON, superpersonId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuperperson(Superperson sp) {
        jdbcTemplate.update(SQL_UPDATE_SUPERPERSON,
                sp.getName(),
                sp.getPower(),
                sp.getDescription(),
                sp.getSuperpersonId());

        // delete bridge table relationships and then reset
        jdbcTemplate.update(SQL_DELETE_SUPERPERSON_ORGANIZATION, sp.getSuperpersonId());

        insertSuperpersonOrganization(sp);
    }

    @Override
    public Superperson getSuperpersonById(int superpersonId) {
        try {
            // get the properties from the superpersons table
            Superperson sp = jdbcTemplate.queryForObject(SQL_SELECT_SUPERPERSON,
                    new SuperpersonMapper(),
                    superpersonId);

            // get and set list on the superperson
            sp.setOrganizations(findOrganizationsForSuperperson(sp));

            return sp;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superperson> getAllSuperpersons() {
        // get all the superpersons
        List<Superperson> spList = jdbcTemplate.query(SQL_SELECT_ALL_SUPERPERSONS,
                new SuperpersonMapper());

        // set the team and lists of date, location, organization, and sightings for each superperson
        return associateOrganizationWithSuperperson(spList);
    }

    @Override
    public List<Superperson> getSuperpersonByOrganization(int organizationId) {
        // get the superperson by organization
        List<Superperson> spList
                = jdbcTemplate.query(SQL_SELECT_SUPERPERSONS_BY_ORGANIZATION_ID,
                        new SuperpersonMapper(),
                        organizationId);
        // set the  organization for each superperson
        return associateOrganizationWithSuperperson(spList);
    }

    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization o = new Organization();
            o.setName(rs.getString("Name"));
            o.setDescription(rs.getString("Description"));
            o.setAddress(rs.getString("Address"));
            o.setContactInfo(rs.getString("ContactInfo"));
            o.setOrganizationId(rs.getInt("OrganizationId"));
            return o;
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

    private void insertSuperpersonOrganization(Superperson sp) {
        final int superpersonId = sp.getSuperpersonId();
        final List<Organization> organizations = sp.getOrganizations();

        //Update bridge table with an entry for each organization a superperson
        //is associated with
        for (Organization currentOrganization : organizations) {
            jdbcTemplate.update(SQL_INSERT_SUPERPERSON_ORGANIZATION,
                    superpersonId,
                    currentOrganization.getOrganizationId());
        }
    }

    private List<Organization> findOrganizationsForSuperperson(Superperson sp) {
        return jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_SUPERPERSON_ID,
                new OrganizationMapper(),
                sp.getSuperpersonId());
    }

    private List<Superperson>
            associateOrganizationWithSuperperson(List<Superperson> spList) {
        // set the complete list of organization ids for each superperson
        for (Superperson currentSP : spList) {

            // add Organizations to current SP
            currentSP.setOrganizations(findOrganizationsForSuperperson(currentSP));

        }
        return spList;
    }

}

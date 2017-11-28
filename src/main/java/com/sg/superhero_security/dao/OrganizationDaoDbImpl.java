/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.dao;

import com.sg.superhero_security.model.Organization;
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
public class OrganizationDaoDbImpl implements OrganizationDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //organization
    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (Name, Description, Address, ContactInfo) "
            + "values (?, ?, ?, ?)";

    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organization where OrganizationId = ?";

    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organization set Name = ?, Description = ?, Address = ?, "
            + "ContactInfo = ? where OrganizationId =  ?";

    private static final String SQL_SELECT_ORGANIZATION
            = "select * from Organization where OrganizationId = ?";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select * from Organization";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization o) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                o.getName(),
                o.getDescription(),
                o.getAddress(),
                o.getContactInfo());

        int organizationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        o.setOrganizationId(organizationId);
    }

    @Override
    public void deleteOrganization(int organizationId) {
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
    }

    @Override
    public void updateOrganization(Organization o) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                o.getName(),
                o.getDescription(),
                o.getAddress(),
                o.getContactInfo(),
                o.getOrganizationId());
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION,
                    new OrganizationMapper(),
                    organizationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
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
}
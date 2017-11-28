/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Sabryna
 */
public class Location {

    private int locationId;
    
    @NotEmpty(message = "You must enter a name for the residence")
    @Length(max = 50, message = "Name must be no more than 50 characters in length.")
    private String nameOfResidence;
    @NotEmpty(message = "You must enter an address")
    @Length(max = 70, message = "Address must be no more than 60 characters in length.")
    private String address;   
    @NotNull(message="You must enter a latitude")
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private BigDecimal latitude;
    @NotNull(message="You must enter a longitude")
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private BigDecimal longitude;
    @NotEmpty(message = "You must enter a description")
    @Length(max = 1000, message = "Address must be no more than 500 characters in length.")
    private String description;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getNameOfResidence() {
        return nameOfResidence;
    }

    public void setNameOfResidence(String nameOfResidence) {
        this.nameOfResidence = nameOfResidence;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.locationId;
        hash = 67 * hash + Objects.hashCode(this.nameOfResidence);
        hash = 67 * hash + Objects.hashCode(this.address);
        hash = 67 * hash + Objects.hashCode(this.latitude);
        hash = 67 * hash + Objects.hashCode(this.longitude);
        hash = 67 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.nameOfResidence, other.nameOfResidence)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

}

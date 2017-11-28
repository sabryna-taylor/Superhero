package com.sg.superhero_security.controller;

import com.sg.superhero_security.model.Location;
import com.sg.superhero_security.model.Organization;
import com.sg.superhero_security.model.Superperson;
import com.sg.superhero_security.service.LocationsService;
import com.sg.superhero_security.service.OrganizationService;
import com.sg.superhero_security.service.SightingsService;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sg.superhero_security.service.SuperpersonService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class PowerController {

    private SuperpersonService spService;
    private LocationsService lService;
    private OrganizationService oService;

    @Inject
    public PowerController(SuperpersonService spService, SightingsService sService,
            LocationsService lService, OrganizationService oService) {
        this.spService = spService;
        this.lService = lService;
        this.oService = oService;
    }

    @RequestMapping(value = "/superpersons", method = RequestMethod.GET)
    public String displaySuperpersons(Model model) {
        List<Superperson> spList = spService.getAllSuperpersons();

        List<Organization> oList = oService.getAllOrganizations();
        model.addAttribute("oList", oList);

        model.addAttribute("spList", spList);

        return "superpersons";
    }

    @RequestMapping(value = "/addNewPerson", method = RequestMethod.POST)
    public String addNewPerson(HttpServletRequest request, Model model) {

        List<Organization> orgList = new ArrayList();
        String[] org = request.getParameterValues("orgsList");

        if (org != null) {
            for (String currentOrgId : org) {
                orgList.add(oService.getOrganizationById(Integer.parseInt(currentOrgId)));
            }
        }

        Superperson sp = new Superperson();
        sp.setName(request.getParameter("name"));
        sp.setPower(request.getParameter("power"));
        sp.setDescription(request.getParameter("description"));
        sp.setOrganizations(orgList);

        spService.addSuperperson(sp);

        return "redirect:superpersons";
    }

    @RequestMapping(value = "/displayPersonDetails", method = RequestMethod.GET)
    public String displayPersonDetails(HttpServletRequest request, Model model) {
        String spIdParameter = request.getParameter("superpersonId");
        int spId = Integer.parseInt(spIdParameter);

        Superperson sp = spService.getSuperpersonById(spId);
        model.addAttribute("sp", sp);

        return "personDetails";
    }

    @RequestMapping(value = "/deletePerson", method = RequestMethod.GET)
    public String deletePerson(HttpServletRequest request) {
        String spIdParameter = request.getParameter("superpersonId");
        int spId = Integer.parseInt(spIdParameter);
        spService.deleteSuperperson(spId);
        return "redirect:superpersons";
    }

    @RequestMapping(value = "/editPersonForm", method = RequestMethod.GET)
    public String editPersonForm(HttpServletRequest request, Model model) {

        String spIdParameter = request.getParameter("superpersonId");
        int spId = Integer.parseInt(spIdParameter);
        Superperson sp = spService.getSuperpersonById(spId);
        model.addAttribute("sp", sp);

        List<Organization> orgs = oService.getAllOrganizations();
        model.addAttribute("orgs", orgs);

        return "editPersonForm";
    }

    @RequestMapping(value = "/editPerson", method = RequestMethod.POST)
    public String editPerson(@Valid
            @ModelAttribute("sp") Superperson sp, BindingResult result,
            HttpServletRequest request, Model model) {

        List<Organization> orgList = new ArrayList();
        String[] org = request.getParameterValues("orgsList");

        if (org != null) {
            for (String currentOrgId : org) {
                orgList.add(oService.getOrganizationById(Integer.parseInt(currentOrgId)));
            }
        }
        sp.setOrganizations(orgList);

        if (result.hasErrors()) {
            List<Organization> orgs = oService.getAllOrganizations();
            model.addAttribute("orgs", orgs);
            return "editPersonForm";
        }
        spService.updateSuperperson(sp);
        return "redirect:superpersons";

    }

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public String displayLocations(Model model) {
        List<Location> lList = lService.getAllLocations();

        model.addAttribute("lList", lList);
        return "locations";
    }

    @RequestMapping(value = "/displayLocationDetails", method = RequestMethod.GET)
    public String displayLocationDetails(HttpServletRequest request, Model model) {
        String lIdParameter = request.getParameter("locationId");
        int lId = Integer.parseInt(lIdParameter);

        Location l = lService.getLocationById(lId);

        model.addAttribute("l", l);

        return "locationDetails";
    }

    @RequestMapping(value = "/addNewLocation", method = RequestMethod.POST)
    public String addNewLocation(HttpServletRequest request, Model model) {

        Location l = new Location();
        l.setNameOfResidence(request.getParameter("nameOfResidence"));
        l.setAddress(request.getParameter("address"));
        l.setLatitude(new BigDecimal(request.getParameter("latitude")));
        l.setLongitude(new BigDecimal(request.getParameter("longitude")));
        l.setDescription(request.getParameter("description"));

        lService.addLocation(l);

        return "redirect:locations";
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request) {
        String lIdParameter = request.getParameter("locationId");
        int lId = Integer.parseInt(lIdParameter);
        lService.deleteLocation(lId);
        return "redirect:locations";
    }

    @RequestMapping(value = "/editLocationForm", method = RequestMethod.GET)
    public String editLocationForm(HttpServletRequest request, Model model) {
        String lIdParameter = request.getParameter("locationId");
        int lId = Integer.parseInt(lIdParameter);
        Location l = lService.getLocationById(lId);
        model.addAttribute("l", l);
        return "editLocationForm";
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public String editLocation(@Valid
            @ModelAttribute("l") Location l, BindingResult result) {
        if (result.hasErrors()) {
            return "editLocationForm";
        } else {
            lService.updateLocation(l);
            return "redirect:locations";
        }
    }

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    public String displayOrganizations(Model model) {
        List<Organization> oList = oService.getAllOrganizations();
        model.addAttribute("oList", oList);
        return "organizations";
    }

    @RequestMapping(value = "/addNewOrganization", method = RequestMethod.POST)
    public String addOrganization(HttpServletRequest request, Model model) {
        Organization o = new Organization();
        o.setName(request.getParameter("name"));
        o.setAddress(request.getParameter("address"));
        o.setDescription(request.getParameter("description"));
        o.setContactInfo(request.getParameter("contactInfo"));

        oService.addOrganization(o);

        return "redirect:organizations";
    }

    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request) {
        String oIdParameter = request.getParameter("organizationId");
        int oId = Integer.parseInt(oIdParameter);
        oService.deleteOrganization(oId);
        return "redirect:organizations";
    }

    @RequestMapping(value = "/editOrganizationForm", method = RequestMethod.GET)
    public String editOrganizationForm(HttpServletRequest request, Model model) {
        String oIdParameter = request.getParameter("organizationId");
        int oId = Integer.parseInt(oIdParameter);
        Organization o = oService.getOrganizationById(oId);
        model.addAttribute("o", o);
        return "editOrganizationForm";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editOrganization(@Valid
            @ModelAttribute("o") Organization o, BindingResult result) {
        if (result.hasErrors()) {
            return "editOrganizationForm";
        } else {
            oService.updateOrganization(o);
            return "redirect:organizations";
        }
    }

    @RequestMapping(value = "/displayOrganizationDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {
        String oIdParameter = request.getParameter("organizationId");
        int oId = Integer.parseInt(oIdParameter);

        Organization o = oService.getOrganizationById(oId);

        model.addAttribute("o", o);

        return "organizationDetails";
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.controller;

import com.sg.superhero_security.model.Location;
import com.sg.superhero_security.model.Sighting;
import com.sg.superhero_security.model.Superperson;
import com.sg.superhero_security.service.LocationsService;
import com.sg.superhero_security.service.SightingsService;
import com.sg.superhero_security.service.SuperpersonService;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Sabryna
 */
@Controller
public class SightingController {

    public final String pictureFolder = "images/";
    private SightingsService sService;
    private LocationsService lService;
    private SuperpersonService spService;

    @Inject
    public SightingController(LocationsService lService, SightingsService sService,
            SuperpersonService spService) {
        this.lService = lService;
        this.sService = sService;
        this.spService = spService;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String displayHomePage(Model model) {

        List<Sighting> sightingsList = sService.getAllSightingsOrderByNameDate();
        model.addAttribute("sortedList", sightingsList);

        return "index";
    }

    @RequestMapping(value = "/sightings", method = RequestMethod.GET)
    public String displaySightings(Model model, HttpServletRequest request) {

        List<Location> lList = lService.getAllLocations();
        model.addAttribute("lList", lList);

        List<Superperson> spList = spService.getAllSuperpersons();
        model.addAttribute("spList", spList);

        List<Sighting> sList = sService.getAllSightingsOrderByNameDate();

        model.addAttribute("sList", sList);

        return "sightings";
    }

    @RequestMapping(value = "/displaySightingDetails", method = RequestMethod.GET)
    public String displaySightingDetails(HttpServletRequest request, Model model) {
        String sIdParameter = request.getParameter("sightingId");
        int sId = Integer.parseInt(sIdParameter);

        Sighting s = sService.getSightingById(sId);

        model.addAttribute("s", s);

        return "sightingDetails";
    }

    @RequestMapping(value = "/addNewSighting", method = RequestMethod.POST)
    public String addNewSighting(HttpServletRequest request, Model model,
            @RequestParam("displayTitle") String displayTitle,
            @RequestParam("fileName") MultipartFile pictureFile) {

        String dateSeen = request.getParameter("dateSeen");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(dateSeen, formatter);

        String superhero = request.getParameter("superpersonId");
        int superheroId = Integer.parseInt(superhero);
        Superperson parsedSuperhero = spService.getSuperpersonById(superheroId);

        String location = request.getParameter("locationId");
        int lId = Integer.parseInt(location);
        Location parsedLocation = lService.getLocationById(lId);

        Sighting sighting = new Sighting();
        sighting.setDateSeen(parsedDate);
        sighting.setSp(parsedSuperhero);
        sighting.setLocation(parsedLocation);
        sighting.setDescription(request.getParameter("description"));

        if (!pictureFile.isEmpty()) {
            try {

                String savePath = request
                        .getSession()
                        .getServletContext()
                        .getRealPath("/") + pictureFolder;
                File dir = new File(savePath);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String filename = pictureFile.getOriginalFilename();
                pictureFile.transferTo(new File(savePath + filename));

                sighting.setFileName(pictureFolder + filename);
                sighting.setTitle(request.getParameter("displayTitle"));

            } catch (Exception e) {
                model.addAttribute("errorMsg", "File upload failed: "
                        + e.getMessage());
            }
        }

        sService.addSighting(sighting);

        return "redirect:sightings";

    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {
        String sIdParameter = request.getParameter("sightingId");
        int sId = Integer.parseInt(sIdParameter);
        sService.deleteSighting(sId);
        return "redirect:sightings";
    }

    @RequestMapping(value = "/editSightingForm", method = RequestMethod.GET)
    public String editSightingForm(HttpServletRequest request, Model model) {
        String sIdParameter = request.getParameter("sightingId");
        int sId = Integer.parseInt(sIdParameter);
        Sighting s = sService.getSightingById(sId);

        //previously set 
        model.addAttribute("sighting", s);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(s.getDateSeen().toString(), formatter);
        model.addAttribute("date", parsedDate);

        //lists
        List<Superperson> superheroes = spService.getAllSuperpersons();
        model.addAttribute("superheroes", superheroes);
        List<Location> l = lService.getAllLocations();
        model.addAttribute("locations", l);
        return "editSightingForm";
    }

    @RequestMapping(value = "/editSighting", method = RequestMethod.POST)
    public String editSighting(HttpServletRequest request,
            @Valid @ModelAttribute("sighting") Sighting s, BindingResult result,
            Model model, @RequestParam("picture") MultipartFile pictureFile) {

        try {

            String savePath = request
                    .getSession()
                    .getServletContext()
                    .getRealPath("/") + pictureFolder;
            File dir = new File(savePath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = pictureFile.getOriginalFilename();
            pictureFile.transferTo(new File(savePath + filename));
            s.setFileName(pictureFolder + filename);
            s.setTitle(request.getParameter("displayTitle"));

        } catch (Exception e) {
            model.addAttribute("errorMsg", "File upload failed: "
                    + e.getMessage());
        }

        if (result.hasErrors()) {
            List<Superperson> superheroes = spService.getAllSuperpersons();
            model.addAttribute("superheroes", superheroes);
            List<Location> l = lService.getAllLocations();
            model.addAttribute("locations", l);

            return "editSightingForm";
        }

        String dateSeen = request.getParameter("date");
        try {
            if (dateSeen != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate parsedDate = LocalDate.parse(dateSeen, formatter);
                s.setDateSeen(parsedDate);
            }
        } catch (DateTimeParseException e) {
            List<Superperson> superheroes = spService.getAllSuperpersons();
            model.addAttribute("superheroes", superheroes);
            List<Location> l = lService.getAllLocations();
            model.addAttribute("locations", l);

            return "editSightingForm";
        }

        sService.updateSighting(s);
        return "redirect:sightings";

    }

}

package com.example.tp.controllers.Celebrite;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tp.exceptions.EntitiesNotFoundExcep;
import com.example.tp.services.AppService;
import com.sun.istack.NotNull;
import com.example.tp.dao.CelebritReservoir;
import com.example.tp.entities.Celebrite;

@RestController
@RequestMapping(path = "json/celebrities")
public class CelebriteRestC {

    @Autowired
    private AppService appService;

    private final CelebritReservoir reservoir;

    CelebriteRestC(CelebritReservoir reservoir) {
        this.reservoir = reservoir;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    // find all celebrities as a list

    @GetMapping
    public List<Celebrite> all() {
        return reservoir.findAll();
    }

    // find all celebrities page by page

    @GetMapping("page/{pageNumber}")
    public List<Celebrite> takeAllCelebrities(Model model, @PathVariable("pageNumber") int currentPage) {
        Sort sort = Sort.by("nom").ascending();
        Page<Celebrite> page = appService.getAllCelebrities(currentPage, 6, sort);
        List<Celebrite> celebrities = page.getContent();

        model.addAttribute("celebrities", celebrities);

        return celebrities;
    }

//    @ResponseStatus(HttpStatus.CREATED)

    // Create a new celebrity

    @PostMapping
    Celebrite newCelebrit(@Valid @NotNull @RequestBody Celebrite newCelebrit) {
        return reservoir.save(newCelebrit);
    }

    // First way to do:
    // Find a celebrity by numCelebrite, with throw exception if doesn't exists!

    @GetMapping(path = "{numCelebrite}")
    Celebrite one(@PathVariable long numCelebrite) {

        return reservoir.findById(numCelebrite)
                .orElseThrow(() -> new EntitiesNotFoundExcep(numCelebrite, "Can't find entered Celebrity with Celebrity Number: "));
    }


    // Second way to do:
    // Find a celebrity by numCelebrite, using Model

    @GetMapping("2/{numCelebrite}")
    public Celebrite getCelebriteById(Model model, @PathVariable("numCelebrite") long numCelebrite) {
        Celebrite celebrite = appService.getCelebriteId(numCelebrite);

        model.addAttribute("celebrite", celebrite);

        return celebrite;
    }

    @GetMapping("prenom/{prenom}")
    public List<Celebrite> getCelebriteByName(@PathVariable String prenom) {
        return appService.getCelebriteName(prenom);
    }

    @GetMapping("nom/{nom}")
    public List<Celebrite> getCelebriteByFamily(@PathVariable String nom) {
        return appService.getCelebriteFamily(nom);
    }

    @GetMapping("contain/{nom}")
    public List<Celebrite> getByNameCelebriteContaining(@PathVariable String nom) {
        return appService.getNameCelebriteContaining(nom);
    }


    //	      @ResponseStatus(HttpStatus.OK)
    @PutMapping
    Celebrite replaceCelebrite(@Valid @NotNull @RequestBody Celebrite newCelebrit) {

        return reservoir.findById(newCelebrit.getNumCelebrite())
                .map(celebrite -> {
                    celebrite.setNom(newCelebrit.getNom());
                    celebrite.setPrenom(newCelebrit.getPrenom());
                    return reservoir.save(celebrite);
                })
                .orElseGet(() -> {
                    return reservoir.save(newCelebrit);
                });
    }

    @DeleteMapping("{numCelebrite}")
    public ResponseEntity<Void> deleteCelebrite(@PathVariable long numCelebrite) {
        try {
            reservoir.deleteById(numCelebrite);
            return ResponseEntity.ok().build();
        } catch (EntitiesNotFoundExcep ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}



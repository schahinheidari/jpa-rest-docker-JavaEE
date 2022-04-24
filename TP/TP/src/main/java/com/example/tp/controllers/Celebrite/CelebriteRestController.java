package com.example.tp.controllers.Celebrite;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
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

import com.example.tp.exceptions.EntitiesNotFoundException;
import com.example.tp.services.AppService;
import com.sun.istack.NotNull;
import com.example.tp.dao.CelebriteRepository;
import com.example.tp.entities.Celebrite;

@RestController
@RequestMapping(path = "json/celebrities")
public class CelebriteRestController {

    @Autowired
    private AppService appService;

    private final CelebriteRepository repository;

    CelebriteRestController(CelebriteRepository repository) {
        this.repository = repository;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    // find all celebrities as a list

    @GetMapping
    public List<Celebrite> all() {
        return repository.findAll();
    }

    // find all celebrities page by page

    @GetMapping("page/{pageNumber}")
    public List<Celebrite> getAllCelebrities(Model model, @PathVariable("pageNumber") int currentPage) {
        Sort sort = Sort.by("nom").ascending();
        Page<Celebrite> page = appService.getAllCelebrities(currentPage, 6, sort);
        List<Celebrite> celebrities = page.getContent();

        model.addAttribute("celebrities", celebrities);

        return celebrities;
    }




//    @ResponseStatus(HttpStatus.CREATED)

    // Create a new celebrity

    @PostMapping
    Celebrite newCelebrite(@Valid @NotNull @RequestBody Celebrite newCelebrite) {
        return repository.save(newCelebrite);
    }

    // First way to do:
    // Find a celebrity by numCelebrite, with throw exception if doesn't exists!

//	  @GetMapping(path = "{numCelebrite}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

    @GetMapping(path = "{numCelebrite}")
    Celebrite one(@PathVariable long numCelebrite) {

        return repository.findById(numCelebrite)
                .orElseThrow(() -> new EntitiesNotFoundException(numCelebrite, "Can't find entered Celebrity with Celebrity Number: "));

    }


    // Second way to do:
    // Find a celebrity by numCelebrite, using Model

    @GetMapping("2/{numCelebrite}")
    public Celebrite getCelebriteById(Model model, @PathVariable("numCelebrite") long numCelebrite) {
        Celebrite celebrite = appService.getCelebriteById(numCelebrite);

        model.addAttribute("celebrite", celebrite);

        return celebrite;
    }



    @GetMapping("prenom/{prenom}")
    public List<Celebrite> getCelebriteByName(@PathVariable String prenom) {
        return appService.getCelebriteByName(prenom);
    }

    @GetMapping("nom/{nom}")
    public List<Celebrite> getCelebriteByFamily(@PathVariable String nom) {
        return appService.getCelebriteByFamily(nom);
    }


    @GetMapping("contain/{nom}")
    public List<Celebrite> getByNameCelebriteContaining(@PathVariable String nom) {
        return appService.getByNameCelebriteContaining(nom);
    }


    //	      @ResponseStatus(HttpStatus.OK)
    @PutMapping
    Celebrite replaceCelebrite(@Valid @NotNull @RequestBody Celebrite newCelebrite) {

        return repository.findById(newCelebrite.getNumCelebrite())
                .map(celebrite -> {
                    celebrite.setNom(newCelebrite.getNom());
                    celebrite.setPrenom(newCelebrite.getPrenom());
                    return repository.save(celebrite);
                })
                .orElseGet(() -> {
                    return repository.save(newCelebrite);
                });
    }

    @DeleteMapping("{numCelebrite}")
    public ResponseEntity<Void> deleteCelebrite(@PathVariable long numCelebrite) {
        try {
            repository.deleteById(numCelebrite);
            return ResponseEntity.ok().build();
        } catch (EntitiesNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}



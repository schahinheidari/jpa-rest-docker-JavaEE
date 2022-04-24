package com.example.tp.controllers.Monument;

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
import com.example.tp.dao.MonumentReservoir;
import com.example.tp.entities.Monument;

@RestController
@RequestMapping("json/monuments")
public class MonumentRestC {

    @Autowired
    private AppService appService;

    private final MonumentReservoir reservoir;

    MonumentRestC(MonumentReservoir reservoir) {
        this.reservoir = reservoir;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    // find all menuments as a list

    @GetMapping
    public List<Monument> all() {
        Sort sort = Sort.by("codeM").ascending();

        return reservoir.findAll(sort);
    }


    // find all monuments page by page

    @GetMapping("page/{pageNumber}")
    public List<Monument> getAllMonuments(Model model, @PathVariable("pageNumber") int thisPage) {
        Sort sort = Sort.by("nomM").ascending();
        Page<Monument> page = appService.getAllMonuments(thisPage, 5, sort);
        List<Monument> monuments = page.getContent();

        model.addAttribute("monuments", monuments);

        return monuments;
    }


    // Create a new monument

    @PostMapping
    Monument newMonument(@Valid @NotNull @RequestBody Monument newMonument) {
        return reservoir.save(newMonument);
    }

    // First way to do:
    // Find a Monuments by codeM, with throw exception if doesn't exists!

    @GetMapping("{codeM}")
    Monument one(@PathVariable String codeM) {

        return reservoir.findById(codeM)
                .orElseThrow(() -> new EntitiesNotFoundExcep("Can't find entered Monument with Code Monument:", codeM));

    }


    @GetMapping("contain/{nom}")
    public List<Monument> getByNameMonumentContaining(@PathVariable String nom) {
        return appService.getNameMonumentContaining(nom);
    }


    @PutMapping
    Monument replaceMonument(@Valid @NotNull @RequestBody Monument newMonument) {

        return reservoir.findById(newMonument.getCodeM())
                .map(monument -> {
                    monument.setNomM(newMonument.getNomM());
                    monument.setProprietaire(newMonument.getProprietaire());
                    monument.setTypeMonument(newMonument.getTypeMonument());
                    monument.setLongitude(newMonument.getLongitude());
                    monument.setLatitude(newMonument.getLatitude());
                    monument.setLieu(newMonument.getLieu());

                    return reservoir.save(monument);
                })
                .orElseGet(() -> {
                    return reservoir.save(newMonument);
                });
    }

    @DeleteMapping("{codeM}")
    public ResponseEntity<Void> deleteMonument(@PathVariable String codeM) {
        try {
            reservoir.deleteById(codeM);
            return ResponseEntity.ok().build();
        } catch (EntitiesNotFoundExcep ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


//Monument

    public void addMonumentToLieu(String codeM, String codeInsee) {
        appService.addMonumentToLieu(codeM, codeInsee);
    }

    public float getDistanceBetweenMonuments(String nomMonA, String nomMonB) {
        return appService.getDistanceBetweenMonuments(nomMonA, nomMonB);
    }

    public List<Monument> getListMonumentsDep(String nomDep) {
        return appService.getListMonumentsDep(nomDep);
    }

    public List<Monument> getListMonumentsLieu(String nomCom) {
        return appService.getListMonumentsLieu(nomCom);
    }


}






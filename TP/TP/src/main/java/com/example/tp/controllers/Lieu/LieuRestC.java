package com.example.tp.controllers.Lieu;


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
import com.example.tp.dao.LieuReservoir;
import com.example.tp.entities.Lieu;

@RestController
@RequestMapping("json/lieux")
public class LieuRestC {

    @Autowired
    private AppService appService;

    private final LieuReservoir reservoir;

    LieuRestC(LieuReservoir reservoir) {
        this.reservoir = reservoir;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // find all lieux as a list

    @GetMapping
    public List<Lieu> all() {
        Sort sort = Sort.by("codeInsee").ascending();

        return reservoir.findAll(sort);
    }


    // find all lieux page by page

    @GetMapping("page/{pageNumber}")
    public List<Lieu> getAllLieux(Model model, @PathVariable("pageNumber") int currentPage) {
        Sort sort = Sort.by("nomCom").ascending();
        Page<Lieu> page = appService.getAllLieux(currentPage, 9, sort);
        List<Lieu> lieux = page.getContent();

        model.addAttribute("lieux", lieux);
        return lieux;
    }


    // Create a new lieu

    @PostMapping
    Lieu newLieu(@Valid @NotNull @RequestBody Lieu newLieu) {
        return reservoir.save(newLieu);
    }

    // First way to do:
    // Find a lieus by codeInsee, with throw exception if doesn't exists!

    @GetMapping("{codeInsee}")
    Lieu one(@PathVariable String codeInsee) {

        return reservoir.findById(codeInsee)
                .orElseThrow(() -> new EntitiesNotFoundExcep("Can't find entered Lieu with Code Insee:",codeInsee));

    }


    @GetMapping("contain/{nom}")
    public List<Lieu> getByNameLieuContaining(@PathVariable String nom) {
        return appService.getNameLieuContaining(nom);
    }



    @PutMapping
    Lieu replaceLieu(@Valid @NotNull @RequestBody Lieu newLieu) {

        return reservoir.findById(newLieu.getCodeInsee())
                .map(lieu -> {
                    lieu.setNomCom(newLieu.getNomCom());
                    lieu.setDepartement(newLieu.getDepartement());

                    return reservoir.save(lieu);
                })
                .orElseGet(() -> {
                    return reservoir.save(newLieu);
                });
    }

    @DeleteMapping("{codeInsee}")
    public ResponseEntity<Void> deleteLieu(@PathVariable String codeInsee) {
        try {
            reservoir.deleteById(codeInsee);
            return ResponseEntity.ok().build();
        } catch (EntitiesNotFoundExcep ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }





}






// Comments



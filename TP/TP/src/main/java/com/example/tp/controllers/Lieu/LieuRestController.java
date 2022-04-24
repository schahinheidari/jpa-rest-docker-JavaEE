package com.example.tp.controllers.Lieu;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tp.exceptions.EntitiesNotFoundException;
import com.example.tp.services.AppService;
import com.sun.istack.NotNull;
import com.example.tp.dao.LieuRepository;
import com.example.tp.entities.Lieu;

@RestController
@RequestMapping("json/lieux")
public class LieuRestController {

    @Autowired
    private AppService appService;

    private final LieuRepository repository;

    LieuRestController(LieuRepository repository) {
        this.repository = repository;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());






    // find all lieux as a list

    @GetMapping
    public List<Lieu> all() {
        Sort sort = Sort.by("codeInsee").ascending();

        return repository.findAll(sort);
    }


    // find all lieux page by page

    @GetMapping("page/{pageNumber}")
    public List<Lieu> getAllLieux(Model model, @PathVariable("pageNumber") int currentPage) {
        Sort sort = Sort.by("nomCom").ascending();
        Page<Lieu> page = appService.getAllLieux(currentPage, 9, sort);
        List<Lieu> lieux = page.getContent();

        model.addAttribute("lieux", lieux);
//	  model.addAttribute("sortField", sortField);
//	  model.addAttribute("sortDirection", sortDirection);

        return lieux;
    }





    // Create a new lieu

    @PostMapping
    Lieu newLieu(@Valid @NotNull @RequestBody Lieu newLieu) {
        return repository.save(newLieu);
    }

    // First way to do:
    // Find a lieus by codeInsee, with throw exception if doesn't exists!

    @GetMapping("{codeInsee}")
    Lieu one(@PathVariable String codeInsee) {

        return repository.findById(codeInsee)
                .orElseThrow(() -> new EntitiesNotFoundException("Can't find entered Lieu with Code Insee:",codeInsee));

    }






    @GetMapping("contain/{nom}")
    public List<Lieu> getByNameLieuContaining(@PathVariable String nom) {
        return appService.getByNameLieuContaining(nom);
    }



    @PutMapping
    Lieu replaceLieu(@Valid @NotNull @RequestBody Lieu newLieu) {

        return repository.findById(newLieu.getCodeInsee())
                .map(lieu -> {
                    lieu.setNomCom(newLieu.getNomCom());
                    lieu.setDepartement(newLieu.getDepartement());

                    return repository.save(lieu);
                })
                .orElseGet(() -> {
                    return repository.save(newLieu);
                });
    }

    @DeleteMapping("{codeInsee}")
    public ResponseEntity<Void> deleteLieu(@PathVariable String codeInsee) {
        try {
            repository.deleteById(codeInsee);
            return ResponseEntity.ok().build();
        } catch (EntitiesNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }





}






// Comments



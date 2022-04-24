package com.example.tp.controllers.Departement;

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
import com.example.tp.dao.DepartementReservoir;
import com.example.tp.entities.Departement;

@RestController
@RequestMapping("json/departements")
public class DepartementRestC {

    @Autowired
    private AppService appService;

    private final DepartementReservoir reservoir;

    DepartementRestC(DepartementReservoir reservoir) {
        this.reservoir = reservoir;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//	@GetMapping(path = "xml", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})

    // find all departements as a list
    @GetMapping
    public List<Departement> all() {
        Sort sort = Sort.by("nomDep").ascending();

        return reservoir.findAll(sort);
    }

    // find all departements page by page

    @GetMapping("page/{pageNumber}")
    public List<Departement> getAllDepartements(Model model, @PathVariable("pageNumber") int currentPage) {
        Sort sort = Sort.by("nomDep").ascending();
        Page<Departement> page = appService.getAllDepartements(currentPage, 5, sort);
        List<Departement> departements = page.getContent();

        model.addAttribute("departements", departements);
//	  model.addAttribute("sortField", sortField);
//	  model.addAttribute("sortDirection", sortDirection);

        return departements;
    }

    // Create a new departement

    @PostMapping
    Departement newDepartement(@Valid @NotNull @RequestBody Departement newDepartement) {
        return reservoir.save(newDepartement);
    }

    // First way to do:
    // Find a departements by numDep, with throw exception if doesn't exists!

    @GetMapping("{numDep}")
    Departement one(@PathVariable String numDep) {

        return reservoir.findById(numDep).orElseThrow(() -> new EntitiesNotFoundExcep("Can't find entered department with numDep:",numDep));

    }


    @GetMapping("contain/{nom}")
    public List<Departement> getNameDepartementContaining(@PathVariable String nom) {
        return appService.getNameDepartementContaining(nom);
    }

    @PutMapping
    Departement replaceDepartement(@Valid @NotNull @RequestBody Departement newDepartement) {

        return reservoir.findById(newDepartement.getNumDep()).map(departement -> {
            departement.setNomDep(newDepartement.getNomDep());
            departement.setLieu(newDepartement.getLieu());

            return reservoir.save(departement);
        }).orElseGet(() -> {
            return reservoir.save(newDepartement);
        });
    }

    @DeleteMapping("{numDep}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable String numDep) {
        try {
            reservoir.deleteById(numDep);
            return ResponseEntity.ok().build();
        } catch (EntitiesNotFoundExcep ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Added part

//  Pouble

}

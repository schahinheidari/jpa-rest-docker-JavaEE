package com.example.tp.controllers.Departement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tp.dao.DepartementReservoir;
import com.example.tp.entities.Departement;
import com.example.tp.entities.Lieu;
import com.example.tp.services.AppService;


@Controller
@RequestMapping("departements")

public class DepartementC {

    @Autowired
    private AppService appService;

    @Autowired
    private DepartementReservoir departementReservoir;

    //    A method for changing the color of cards
    public void color(Model model) {
        int n = (int) (Math.random() * 7 + 1);
        List<String> border = Arrays.asList("border-primary","border-secondary","border-success","border-danger","border-warning","border-info","border-light","border-dark");
        List<String> text   = Arrays.asList("text-primary","text-secondary","text-success","text-danger","text-warning","text-info","text-light","text-dark");
        model.addAttribute("border", border.get(n));
        model.addAttribute("text", text.get(n));
        model.addAttribute("n", n);

    }



// ---------------------------- start Departements   ----------------------------

    //	Departement Search
    @PostMapping("search")
    public String searchId(Model model, @ModelAttribute("sampleEntity") Departement sampleEntity) {
        try {
            Departement dep = appService.getDepartement(sampleEntity.getNumDep());

            System.out.println("dep:::::" + dep);

            if(dep != null) model.addAttribute("sampleEntity",dep);
            model.addAttribute("mode","details");


        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "departement/update";
    }



    @GetMapping(value="page/{pageNumber}")
    public String allDepartements(Model model, @PathVariable("pageNumber") int currentPage, @RequestParam(defaultValue="nomDep") String sortField, @RequestParam(defaultValue="asc") String sortDirection)   {
        try {
            Sort sort = Sort.by(sortField);
            sort=  sortDirection.equals("asc") ? sort.ascending() : sort.descending();
            Page<Departement> page = appService.getAllDepartements(currentPage, 15, sort);
            List<Departement> departements = page.getContent();
            model.addAttribute("departements", departements);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            color(model);

            model.addAttribute("sampleEntity", new Departement());
            model.addAttribute("departements", departements);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("sortField", "nomDep");
            model.addAttribute("sortDirection", "asc");
            String reverseSortDirection = sortDirection.equals("asc") ? "desc" : "asc";
            model.addAttribute("reverseSortDirection", reverseSortDirection);



        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "departement/all";
    }


//Departements card

    @GetMapping("card/page/{pageNumber}")
    public String allDepartementsCard(Model model, @PathVariable("pageNumber") int currentPage) {
        try {
            Sort sort = Sort.by("nomDep").descending();
            Page<Departement> page = appService.getAllDepartements(currentPage, 6, sort);
            List<Departement> departements = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            color(model);

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("departements", departements);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);

            int n = (int) (Math.random() * 8 + 1);
            List<String> border = Arrays.asList("border-primary","border-secondary","border-success","border-danger","border-warning","border-info","border-light","border-dark");
            List<String> text   = Arrays.asList("text-primary","text-secondary","text-success","text-danger","text-warning","text-info","text-light","text-dark");
            model.addAttribute("border", border.get(n));
            model.addAttribute("text", text.get(n));
            model.addAttribute("n", n);

        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "departement/card";
    }








    @GetMapping("table")
    public String table(Model model) {
        try {
            //   List<Departement> departements = appService.findAll();

            color(model);

            //   model.addAttribute("departements", departements);


        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "departement/table";
    }



    @GetMapping("data-table")
    public String datatable(Model model) {
        try {
            List<Departement> departements = appService.findAll();

            color(model);

            model.addAttribute("departements", departements);


        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "departement/data-table";
    }




    //  Departement New Form
//  Departement update form
//  Departement Details Form
    @GetMapping(value = {"{mode}", "{mode}/{id}"})
    public String monumentForm(Model model, @ModelAttribute("sampleEntity") Departement sampleEntity, @PathVariable(value="id") Optional<String> id, @PathVariable(value="mode") String mode) {
        color(model);

        try {
            if(id.isPresent()) {
                model.addAttribute("id", id.get());
                Departement existedSampleEntity = appService.getDepartement(id.get());
                model.addAttribute("sampleEntity",existedSampleEntity);
            }
            else {
                model.addAttribute("id","none");
                model.addAttribute("state","add");
                model.addAttribute("sampleEntity",new Departement());

            }

        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        model.addAttribute("mode", mode);

        return "departement/update";

    }


    //	Departement New
//	Departement Update
    @PostMapping
    public String saveDepartement(Model model, @Valid @NotNull @ModelAttribute("sampleEntity") Departement sampleEntity, BindingResult result, RedirectAttributes ra){

        Lieu lieu = sampleEntity.setLieu(appService.getLieu(sampleEntity.getLieu().getCodeInsee()));

        if(lieu != null) sampleEntity.setLieu(lieu);
        else sampleEntity.setLieu(new Lieu(sampleEntity.getLieu().getCodeInsee()));
        appService.addDepartement(sampleEntity);
        if(result.hasErrors()){
            ra.addAttribute("message", result.getAllErrors());
            return "departement/update";
        }
        ra.addFlashAttribute("sampleEntity", sampleEntity);
        ra.addAttribute("message", sampleEntity.getNumDep() + " updated successfully");

        return "redirect:/departements/page/1";
    }


    //		Departement delete
    @GetMapping("delete/{id}")
    public String deleteDepartementId(Model model, @PathVariable String id) {
        try {
            appService.deleteDepartementId(id);
            model.addAttribute("message",id +" is successfully deleted");
        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "redirect:/departements/page/1";
    }


}
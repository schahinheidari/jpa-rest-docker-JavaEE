package com.example.tp.controllers.Celebrite;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
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

import com.example.tp.dao.CelebritReservoir;
import com.example.tp.entities.Celebrite;

import com.example.tp.services.AppService;



@Controller
@RequestMapping("celebritis")

public class CelebriteC {

    @Autowired
    private AppService appService;

    @Autowired
    private CelebritReservoir celebriteRepository;

    //    A method for changing the color of cards
    public void color(Model model) {
        int n = (int) (Math.random() * 7 + 1);
        List<String> border = Arrays.asList("border-primary","border-secondary","border-success","border-danger","border-warning","border-info","border-light","border-dark");
        List<String> text   = Arrays.asList("text-primary","text-secondary","text-success","text-danger","text-warning","text-info","text-light","text-dark");
        model.addAttribute("border", border.get(n));
        model.addAttribute("text", text.get(n));
        model.addAttribute("n", n);

    }

// ---------------------------- start Celebrite   ----------------------------

    //	Celebrite Display
    @GetMapping("display")
    public String display() {
        return "celebrite/display";
    }

    //  celebritis pagination
    @GetMapping("page/{pageNumber}")
    @Secured(value = { "ROLE_ADMIN","ROLE_VOYAGISTE","ROLE_TOURISTE"})
    public String allcelebritis(Model model, @PathVariable("pageNumber") int thisPage, @RequestParam(defaultValue="numCelebrite") String sortField, @RequestParam(defaultValue="asc") String sortDirection)   {
        try {
            Sort sort = Sort.by(sortField);
            sort=  sortDirection.equals("asc") ? sort.ascending() : sort.descending();

            Page<Celebrite> page = appService.getAllCelebrities(thisPage, 15, sort);
            List<Celebrite> celebritis = page.getContent();
            long totalParts = page.getTotalElements();
            int totalPages = page.getTotalPages();
            color(model);
            model.addAttribute("celebritis", celebritis);
            model.addAttribute("totalItems", totalParts);
            model.addAttribute("thisPage", thisPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("sortField", "numCelebrite");
            model.addAttribute("sortDirection", "asc");
            String storeSortDirection = sortDirection.equals("asc") ? "desc" : "asc";
            model.addAttribute("reverseSortDirection", storeSortDirection);
        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "celebrite/all";
    }


    //    celebritis card
    @Secured(value = { "ROLE_ADMIN","ROLE_VOYAGISTE","ROLE_TOURISTE"})
    @GetMapping("card/page/{pageNumber}")
    public String allcelebritisSheet(Model model, @PathVariable("pageNumber") int thisPage) {
        try {
            Sort sort = Sort.by("nom").ascending();

            Page<Celebrite> page = appService.getAllCelebrities(thisPage, 6, sort);
            List<Celebrite> celebritis = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            color(model);
            model.addAttribute("thisPage", thisPage);
            model.addAttribute("celebritis", celebritis);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "celebrite/card";
    }


    //  Celebrite New Form
//  Celebrite update form
//  Celebrite Details Form
    @Secured(value = { "ROLE_ADMIN"})
    @GetMapping(value = {"{mode}", "{mode}/{id}"})
    public String celebritForm(Model model, @ModelAttribute("sampleEntity") Celebrite sampleEntity, @PathVariable(value="id") Optional<Long> id, @PathVariable(value="mode") String mode) {
        color(model);

        try {
            if(id.isPresent()) {
                model.addAttribute("id", id.get());
                Celebrite existedSampleEntity = appService.getCelebriteId(id.get());
                model.addAttribute("sampleEntity",existedSampleEntity);
            }
            else {
                model.addAttribute("id","none");
                model.addAttribute("state","add");
                model.addAttribute("sampleEntity",new Celebrite());

            }

        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        model.addAttribute("mode", mode);

        return "celebrite/update";

    }

    //	Celebrite New
//	Celebrite Update
    @PostMapping
    @Secured(value = { "ROLE_ADMIN"})
    public String saveCelebrite(Model model, @Valid @NotNull @ModelAttribute("sampleEntity") Celebrite sampleEntity, BindingResult result, RedirectAttributes ra){

        appService.addCelebrite(sampleEntity);
        if(result.hasErrors()){
            ra.addAttribute("message", result.getAllErrors());
            return "celebrite/update";
        }
        ra.addFlashAttribute("sampleEntity", sampleEntity);
        ra.addAttribute("message", sampleEntity.getNumCelebrite() + " updated successfully");

        return "redirect:/celebritis/page/1";
    }

    //    Celebrity delete
    @Secured(value = { "ROLE_ADMIN"})
    @GetMapping("delete/{id}")
    public String deleteCelebriteById(Model model, @PathVariable long id, RedirectAttributes ra) {
        try {
            appService.deleteCelebriteId(id);
            model.addAttribute("message",id +"is successfully deleted");
        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "redirect:/celebritis/page/1";
    }




}

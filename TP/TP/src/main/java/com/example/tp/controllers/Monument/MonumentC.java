package com.example.tp.controllers.Monument;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tp.dao.MonumentReservoir;
import com.example.tp.entities.Monument;
import com.example.tp.entities.Lieu;

import com.example.tp.services.AppService;



@Controller
@RequestMapping("monuments")

public class MonumentC {

    @Autowired
    private AppService appService;

    @Autowired
    private MonumentReservoir monumentReservoir;


    //    A method for changing the color of cards
    public void color(Model model) {
        int n = (int) (Math.random() * 7 + 1);
        List<String> border = Arrays.asList("border-primary","border-secondary","border-success","border-danger","border-warning","border-info","border-light","border-dark");
        List<String> text   = Arrays.asList("text-primary","text-secondary","text-success","text-danger","text-warning","text-info","text-light","text-dark");
        model.addAttribute("border", border.get(n));
        model.addAttribute("text", text.get(n));
        model.addAttribute("n", n);

    }

// ---------------------------- start Monument ----------------------------

    //	Monument Display
    @GetMapping("display")
    public String display() {
        return "monument/display";
    }


    //  Monument all pagiation
    @GetMapping("page/{pageNumber}")
    public String allMonuments(Model model, @PathVariable("pageNumber") int thisPage, @RequestParam(defaultValue="codeM") String sortField, @RequestParam(defaultValue="asc") String sortDirection)   {
        try {
            Sort sort = Sort.by(sortField);
            sort=  sortDirection.equals("asc") ? sort.ascending() : sort.descending();
            Page<Monument> page = appService.getAllMonuments(thisPage, 15, sort);
            List<Monument> monuments = page.getContent();
            long totalParts = page.getTotalElements();
            int totalPages = page.getTotalPages();

            color(model);

            model.addAttribute("thisPage", thisPage);
            model.addAttribute("monuments", monuments);
            model.addAttribute("totalParts", totalParts);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("sortField", "codeM");
            model.addAttribute("sortDirection", "asc");
            String reverseSortDirection = sortDirection.equals("asc") ? "desc" : "asc";
            model.addAttribute("reverseSortDirection", reverseSortDirection);


        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "monument/all";
    }

//    @RequestParam(required = false, defaultValue = "someValue").

    //  Monuments card pagination
    @GetMapping("card/page/{pageNumber}")
    public String allMonumentsCard(Model model, @PathVariable("pageNumber") int thisPage) {
        try {
            Sort sort = Sort.by("codeM").descending();
            Page<Monument> page = appService.getAllMonuments(thisPage, 6, sort);
            List<Monument> monuments = page.getContent();
            long totalParts = page.getTotalElements();
            int totalPages = page.getTotalPages();

            color(model);

            model.addAttribute("thisPage", thisPage);
            model.addAttribute("monuments", monuments);
            model.addAttribute("totalParts", totalParts);
            model.addAttribute("totalPages", totalPages);


        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "monument/card";
    }

    //  Monument New Form
//  Monument update form
//  Monument Details Form
    @GetMapping(value = {"{mode}", "{mode}/{id}"})
    public String monumentForm(Model model, @ModelAttribute("sampleEntity") Monument sampleEntity, @PathVariable(value="id") Optional<String> id, @PathVariable(value="mode") String mode) {
        color(model);

        try {
            if(id.isPresent()) {
                model.addAttribute("id", id.get());
                Monument existedSampleEntity = appService.getMonument(id.get());
                model.addAttribute("sampleEntity",existedSampleEntity);
            }
            else {
                model.addAttribute("id","none");
                model.addAttribute("state","add");
                model.addAttribute("sampleEntity",new Monument());

            }

        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        model.addAttribute("mode", mode);

        return "monument/update";

    }


    //	Monument New
//	Monument Update
    @PostMapping
    public String saveMonument(Model model, @Valid @NotNull @ModelAttribute("sampleEntity") Monument sampleEntity, BindingResult result, RedirectAttributes ra){

        Lieu lieu = sampleEntity.setLieu(appService.getLieu(sampleEntity.getLieu().getCodeInsee()));

        if(lieu != null) sampleEntity.setLieu(lieu);
        else sampleEntity.setLieu(new Lieu(sampleEntity.getLieu().getCodeInsee()));
        appService.addMonument(sampleEntity);
        if(result.hasErrors()){
            ra.addAttribute("message", result.getAllErrors());
            return "monument/update";
        }
        ra.addFlashAttribute("sampleEntity", sampleEntity);
        ra.addAttribute("message", sampleEntity.getCodeM() + " updated successfully");

        return "redirect:/monuments/page/1";
    }

    //		Monument delete
    @GetMapping("delete/{id}")
    public String deleteMonumentId(Model model, @PathVariable String id) {
        try {
            appService.deleteMonumentId(id);
            model.addAttribute("message",id +" is successfully deleted");
        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "redirect:/monuments/page/1";
    }


// ---------------------------- End Monument   ----------------------------






}

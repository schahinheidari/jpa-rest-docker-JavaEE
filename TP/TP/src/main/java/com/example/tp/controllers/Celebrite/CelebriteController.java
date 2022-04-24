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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tp.dao.CelebriteRepository;
import com.example.tp.entities.Celebrite;

import com.example.tp.entities.Monument;
import com.example.tp.services.AppService;



@Controller
@RequestMapping("celebrities")

public class CelebriteController {

    @Autowired
    private AppService appService;

    @Autowired
    private CelebriteRepository celebriteRepository;




    //    A method for changing the color of cards
    public void color(Model model) {
        int n = (int) (Math.random() * 7 + 1);
        List<String> border = Arrays.asList("border-primary","border-secondary","border-success","border-danger","border-warning","border-info","border-light","border-dark");
        List<String> text   = Arrays.asList("text-primary","text-secondary","text-success","text-danger","text-warning","text-info","text-light","text-dark");
        model.addAttribute("border", border.get(n));
        model.addAttribute("text", text.get(n));
        model.addAttribute("n", n);

    }



// ---------------------------- Begin Celebrite   ----------------------------


    //	Celebrite Display
    @GetMapping("display")
    public String display() {
        return "celebrite/display";
    }


    //  Celebrities pagination
    @GetMapping("page/{pageNumber}")
    @Secured(value = { "ROLE_ADMIN","ROLE_VOYAGISTE","ROLE_TOURISTE"})
    public String allCelebrities(Model model, @PathVariable("pageNumber") int currentPage, @RequestParam(defaultValue="numCelebrite") String sortField, @RequestParam(defaultValue="asc") String sortDirection)   {
        try {
            Sort sort = Sort.by(sortField);
            sort=  sortDirection.equals("asc") ? sort.ascending() : sort.descending();

            Page<Celebrite> page = appService.getAllCelebrities(currentPage, 15, sort);
            List<Celebrite> celebrities = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            color(model);
            model.addAttribute("celebrities", celebrities);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("sortField", "numCelebrite");
            model.addAttribute("sortDirection", "asc");
            String reverseSortDirection = sortDirection.equals("asc") ? "desc" : "asc";
            model.addAttribute("reverseSortDirection", reverseSortDirection);
        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "celebrite/all";
    }


    //    Celebrities card
    @Secured(value = { "ROLE_ADMIN","ROLE_VOYAGISTE","ROLE_TOURISTE"})
    @GetMapping("card/page/{pageNumber}")
    public String allCelebritiesCard(Model model, @PathVariable("pageNumber") int currentPage) {
        try {
            Sort sort = Sort.by("nom").ascending();

            Page<Celebrite> page = appService.getAllCelebrities(currentPage, 6, sort);
            List<Celebrite> celebrities = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            color(model);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("celebrities", celebrities);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "celebrite/card";
    }


// //    Celebrities card details
//   @GetMapping("details/{numCelebrite}")
//   public String celebritiesCardDetails(Model model, @PathVariable long numCelebrite) {
//       try {
//           Celebrite celebrite = appService.getCelebriteById(numCelebrite);
//           model.addAttribute("celebrite", celebrite);
//           color(model);
//       }catch (Exception e) {
//           model.addAttribute("message",e);
//       }
//       return "celebrite/details";
//   }




    //  Celebrite New Form
//  Celebrite update form
//  Celebrite Details Form
    @Secured(value = { "ROLE_ADMIN"})
    @GetMapping(value = {"{mode}", "{mode}/{id}"})
    public String celebriteForm(Model model, @ModelAttribute("sampleEntity") Celebrite sampleEntity, @PathVariable(value="id") Optional<Long> id, @PathVariable(value="mode") String mode) {
        color(model);

        try {
            if(id.isPresent()) {
                model.addAttribute("id", id.get());
                Celebrite existedSampleEntity = appService.getCelebriteById(id.get());
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

//    Monument monument = sampleEntity.setMonument(appService.getMonument(sampleEntity.getMonument().getCodeM()));
//
//    if(monument != null) sampleEntity.setMonument(monument);
//    else sampleEntity.setMonument(new Monument(sampleEntity.getMonument().getCodeM()));
        appService.addCelebrite(sampleEntity);
        if(result.hasErrors()){
            ra.addAttribute("message", result.getAllErrors());
            return "celebrite/update";
        }
        ra.addFlashAttribute("sampleEntity", sampleEntity);
        ra.addAttribute("message", sampleEntity.getNumCelebrite() + " updated successfully");

        return "redirect:/celebrities/page/1";
    }



    //    Celebrity delete
    @Secured(value = { "ROLE_ADMIN"})
    @GetMapping("delete/{id}")
    public String deleteCelebriteById(Model model, @PathVariable long id, RedirectAttributes ra) {
        try {
            appService.deleteCelebriteById(id);
            model.addAttribute("message",id +"is successfully deleted");
        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "redirect:/celebrities/page/1";
    }




// ---------------------------- End Celebrite   ----------------------------





}

package com.example.tp.controllers.Lieu;

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

import com.example.tp.dao.LieuRepository;
import com.example.tp.entities.Lieu;
import com.example.tp.entities.Departement;

import com.example.tp.services.AppService;



@Controller
@RequestMapping("lieux")

public class LieuController {

    @Autowired
    private AppService appService;

    @Autowired
    private LieuRepository lieuRepository;



    //    A method for changing the color of cards
    public void color(Model model) {
        int n = (int) (Math.random() * 7 + 1);
        List<String> border = Arrays.asList("border-primary","border-secondary","border-success","border-danger","border-warning","border-info","border-light","border-dark");
        List<String> text   = Arrays.asList("text-primary","text-secondary","text-success","text-danger","text-warning","text-info","text-light","text-dark");
        model.addAttribute("border", border.get(n));
        model.addAttribute("text", text.get(n));
        model.addAttribute("n", n);

    }





// ---------------------------- Begin Lieux   ----------------------------

    //	Lieu Display
    @GetMapping("display")
    public String display() {
        return "lieu/display";
    }




    //  Lieux all table pagination
    @GetMapping("page/{pageNumber}")
    public String allLieux(Model model, @PathVariable("pageNumber") int currentPage, @RequestParam(defaultValue="codeInsee") String sortField, @RequestParam(defaultValue="asc") String sortDirection)   {
        try {
            Sort sort = Sort.by(sortField);
            sort=  sortDirection.equals("asc") ? sort.ascending() : sort.descending();
            Page<Lieu> page = appService.getAllLieux(currentPage, 12, sort);
            List<Lieu> lieux = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            color(model);

            model.addAttribute("lieux", lieux);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("sortField", "codeInsee");
            model.addAttribute("sortDirection", "asc");
            String reverseSortDirection = sortDirection.equals("asc") ? "desc" : "asc";
            model.addAttribute("reverseSortDirection", reverseSortDirection);




        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "lieu/all";
    }


    //  Lieux card pagination
    @GetMapping("card/page/{pageNumber}")
    public String allLieuxCard(Model model, @PathVariable("pageNumber") int currentPage)   {
        try {
            Sort sort = Sort.by("codeInsee").ascending();
            Page<Lieu> page = appService.getAllLieux(currentPage, 9, sort);
            List<Lieu> lieux = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();


            color(model);

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("lieux", lieux);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);



        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "lieu/card";
    }






//    example
//    @RequestMapping(value = {"/article", "/article/{id}"}")
//    public Article getArticle(@PathVariable Optional<Integer> optionalArticleId) {
//        if (optionalArticleId.isPresent()) {
//            Integer articleId = optionalArticleId.get();
//            //...
//        } else {
//            //...
//        }
//    }


//    Optional<String> opt = Optional.of("baeldung");
//    opt.ifPresent(name -> System.out.println(name.length()));
//    String name = Optional.ofNullable(nullName).orElse("john");
//    String name = Optional.ofNullable(nullName).orElseGet(() -> "john");
//    String name = Optional.ofNullable(nullName).orElseThrow(
//    	      IllegalArgumentException::new);
//    	}

//    Integer year = 2016;
//    Optional<Integer> yearOptional = Optional.of(year);
//    boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
//    assertTrue(is2016);
//    boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
//    assertFalse(is2017);





    //    Lieu New Form
//    Lieux update form
//    Lieu Details Form
    @GetMapping(value = {"{mode}", "{mode}/{id}"})
    String lieuForm(Model model, @ModelAttribute("sampleEntity") Lieu sampleEntity, @PathVariable(value="id") Optional<String> id, @PathVariable(value="mode") String mode) {
        color(model);

        try {
            if(id.isPresent()) {
                model.addAttribute("id", id.get());
                Lieu existedSampleEntity = appService.getLieu(id.get());
                model.addAttribute("sampleEntity",existedSampleEntity);
            }
            else {
                model.addAttribute("id","none");
                model.addAttribute("state","add");
                model.addAttribute("sampleEntity",new Lieu());

            }

        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        model.addAttribute("mode", mode);
//
//		  if(id.isPresent()) {
//			  Lieu existedSampleEntity = appService.getLieu(id.get());
//			  model.addAttribute("sampleEntity",existedSampleEntity);
//			  if(mode == "details") {
//				  model.addAttribute("state","details");
//			  }
//			  if(mode == "update") {
//	            try {
//	                model.addAttribute("state","update");
//	            }catch (Exception e) {
//	                model.addAttribute("message",e);
//	            }
//
//			  }
//		  }
//

        return "lieu/update";

    }


    //	  Lieux New
//	  Lieux Update
    @PostMapping
    public String saveLieu(Model model, @Valid @NotNull @ModelAttribute("sampleEntity") Lieu sampleEntity, BindingResult result, RedirectAttributes ra){

        Departement dep = sampleEntity.setDepartement(appService.getDepartement(sampleEntity.getDepartement().getNumDep()));

        if(dep != null) sampleEntity.setDepartement(dep);
        else sampleEntity.setDepartement(new Departement(sampleEntity.getDepartement().getNumDep()));
        appService.addLieu(sampleEntity);
        if(result.hasErrors()){
            ra.addAttribute("message", result.getAllErrors());
            return "lieu/update";
        }
        ra.addFlashAttribute("sampleEntity", sampleEntity);
        ra.addAttribute("message", sampleEntity.getCodeInsee() + " updated successfully");

        return "redirect:/lieux/page/1";
    }



    //		Lieux delete
    @GetMapping("delete/{id}")
    public String deleteLieuById(Model model, @PathVariable String id, RedirectAttributes ra) {
        try {
            appService.deleteLieuById(id);
            model.addAttribute("message",id +" is successfully deleted");
        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "redirect:/lieux/page/1";
    }




// ---------------------------- End Lieux ---------------------------------






}
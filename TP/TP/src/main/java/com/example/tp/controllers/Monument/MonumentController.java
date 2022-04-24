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

import com.example.tp.dao.MonumentRepository;
import com.example.tp.entities.Monument;
import com.example.tp.entities.Lieu;

import com.example.tp.services.AppService;



@Controller
@RequestMapping("monuments")

public class MonumentController {

    @Autowired
    private AppService appService;

    @Autowired
    private MonumentRepository monumentRepository;


    //    A method for changing the color of cards
    public void color(Model model) {
        int n = (int) (Math.random() * 7 + 1);
        List<String> border = Arrays.asList("border-primary","border-secondary","border-success","border-danger","border-warning","border-info","border-light","border-dark");
        List<String> text   = Arrays.asList("text-primary","text-secondary","text-success","text-danger","text-warning","text-info","text-light","text-dark");
        model.addAttribute("border", border.get(n));
        model.addAttribute("text", text.get(n));
        model.addAttribute("n", n);

    }





// ---------------------------- Begin Monument ----------------------------

    //	Monument Display
    @GetMapping("display")
    public String display() {
        return "monument/display";
    }


    //  Monument all pagiation
    @GetMapping("page/{pageNumber}")
    public String allMonuments(Model model, @PathVariable("pageNumber") int currentPage, @RequestParam(defaultValue="codeM") String sortField, @RequestParam(defaultValue="asc") String sortDirection)   {
        try {
            Sort sort = Sort.by(sortField);
            sort=  sortDirection.equals("asc") ? sort.ascending() : sort.descending();
            Page<Monument> page = appService.getAllMonuments(currentPage, 15, sort);
            List<Monument> monuments = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            color(model);

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("monuments", monuments);
            model.addAttribute("totalItems", totalItems);
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
    public String allMonumentsCard(Model model, @PathVariable("pageNumber") int currentPage) {
        try {
            Sort sort = Sort.by("codeM").descending();
            Page<Monument> page = appService.getAllMonuments(currentPage, 6, sort);
            List<Monument> monuments = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            color(model);

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("monuments", monuments);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);


        }catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "monument/card";
    }



// //    Monument New form
// 	  @GetMapping("add")
// 	  public String add(Model model, @ModelAttribute("newMonument") Monument newMonument) {

//           color(model);
//           model.addAttribute("newMonument",new Monument());

// 	    return "monument/add";

// 	  }

// //	    Monument New
// 	    @PostMapping("monuments")
// 	    public String saveMonument(@Valid @NotNull @ModelAttribute("newMonument") Monument newMonument, BindingResult result, RedirectAttributes ra){
// 	        appService.addMonument(newMonument);
// 	        if(result.hasErrors()){
// 	            return "monument/add";
// 	        }
// 	        ra.addFlashAttribute("newMonument", newMonument);
// 	        return "redirect:/monuments/card/page/1";
// 	    }


// //	    Monument update form
//         @GetMapping("monuments/update/{id}")
//         public String monumentUpdateForm(Model model, @PathVariable(value="id") String id) {
//             try {
//                 color(model);
//                 Monument sampleEntity = appService.getMonument(id);
//                 model.addAttribute("sampleEntity",sampleEntity);
//             }catch (Exception e) {
//                 model.addAttribute("message",e);
//             }
//         return "monument/update";
//         }


// //	Monument update
//     @PostMapping("monuments/update")
//     public String monumentsUpdate(Model model, @ModelAttribute("sampleEntity") Monument sampleEntity, @ModelAttribute("id") String id) {
//         try {
//             model.addAttribute("message", sampleEntity.getCodeM() + " updated successfully");
//             appService.addMonument(sampleEntity);
// 	    } catch (Exception e) {
// 	        model.addAttribute("message", e);
// 	    	}
// 	        return "redirect:/monuments/page/1";
//     	}












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
    public String deleteMonumentById(Model model, @PathVariable String id) {
        try {
            appService.deleteMonumentById(id);
            model.addAttribute("message",id +" is successfully deleted");
        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "redirect:/monuments/page/1";
    }


// ---------------------------- End Monument   ----------------------------






}

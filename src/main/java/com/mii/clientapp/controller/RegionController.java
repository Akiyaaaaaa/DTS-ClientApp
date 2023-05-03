package com.mii.clientapp.controller;

import com.mii.clientapp.model.Region;
import com.mii.clientapp.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/region")
@AllArgsConstructor
public class RegionController {

    private RegionService regionService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("regions", regionService.getAll());
        return "region/index";
    }

    @GetMapping("/create")
    public String createView(Region region){
        return "region/create-form";
    }

    @PostMapping
    public String create(Region region){
        regionService.create(region);
        return "redirect:/region";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable long id, Model model){
        model.addAttribute("region",regionService.getById(id));
        return "region/update-form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Region region){
        regionService.update(id,region);
        return "redirect:/region";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id){
        regionService.delete(id);
        return "redirect:/region";
    }

}

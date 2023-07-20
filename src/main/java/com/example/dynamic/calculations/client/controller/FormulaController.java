package com.example.dynamic.calculations.client.controller;

import com.example.dynamic.calculations.client.model.Formula;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/formulas")
public class FormulaController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String resourceUrl = "http://localhost:8080/api/v1/formulas";

    @GetMapping()
    public String index(Model model, @ModelAttribute("formula") Formula formula) {
        List<Formula> response
                = restTemplate.exchange(
                        resourceUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Formula>>() {})
                .getBody();

        model.addAttribute("formulas", response);
        return "index";
    }

    @PostMapping()
    public String newFormula(@ModelAttribute("formula") @Valid Formula formula,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        HttpEntity<Formula> request = new HttpEntity<>(formula);
        restTemplate.postForObject(resourceUrl, request, Formula.class);

        return "redirect:/formulas";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Formula response
                = restTemplate.getForObject(resourceUrl + "/" + id, Formula.class);
        model.addAttribute("formula", response);
        return "show";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("formula") Formula updatedFormula, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "edit";

        HttpEntity<Formula> requestUpdate = new HttpEntity<>(updatedFormula);
        restTemplate.exchange(resourceUrl + "/" + id, HttpMethod.PUT, requestUpdate, Void.class);
        return "redirect:/formulas";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Formula response
                = restTemplate.getForObject(resourceUrl + "/" + id, Formula.class);
        model.addAttribute("formula", response);
        return "edit";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        restTemplate.delete(resourceUrl + "/" + id);
        return "redirect:/formulas";
    }


}

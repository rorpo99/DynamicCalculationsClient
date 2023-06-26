package com.example.dynamic.calculations.client.controller;

import com.example.dynamic.calculations.client.model.Formula;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/formulas")
public class FormulaController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping()
    public String index(Model model, @ModelAttribute("formula") Formula formula) {
        String fooResourceUrl
                = "http://localhost:8080/api/v1/formulas";
//        ResponseEntity<Formula> response
//                = restTemplate.getForEntity(fooResourceUrl, Formula.class);
        List<Formula> response
                = restTemplate.exchange(
                        fooResourceUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Formula>>() {})
                .getBody();

        Objects.requireNonNull(response).sort(Comparator.comparingInt(Formula::getId));

        model.addAttribute("formulas", response);
        return "index";
    }

    @PostMapping()
    public String newFormula(@ModelAttribute("formula") @Valid Formula formula,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        }

        String fooResourceUrl
                = "http://localhost:8080/api/v1/formulas";

        HttpEntity<Formula> request = new HttpEntity<>(formula);
        Formula newformula = restTemplate.postForObject(fooResourceUrl, request, Formula.class);

        return "redirect:/formulas";

    }

//    @GetMapping("/new")
//    public String newFormula(@ModelAttribute("formula") Formula formula) {
//        return "new";
//    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        String fooResourceUrl
                = "http://localhost:8080/api/v1/formulas";

        Formula response
                = restTemplate.getForObject(fooResourceUrl + "/" + id, Formula.class);
        model.addAttribute("formula", response);
        return "show";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("formula") Formula updatedFormula, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "edit";

        //personDAO.update(id, person);
        String fooResourceUrl
                = "http://localhost:8080/api/v1/formulas";

        HttpEntity<Formula> requestUpdate = new HttpEntity<>(updatedFormula);
        restTemplate.exchange(fooResourceUrl + "/" + id, HttpMethod.PUT, requestUpdate, Void.class);
        return "redirect:/formulas";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        String fooResourceUrl
                = "http://localhost:8080/api/v1/formulas";
        Formula response
                = restTemplate.getForObject(fooResourceUrl + "/" + id, Formula.class);
        model.addAttribute("formula", response);
        return "edit";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        String fooResourceUrl
                = "http://localhost:8080/api/v1/formulas";
        restTemplate.delete(fooResourceUrl + "/" + id);
        return "redirect:/formulas";
    }


}

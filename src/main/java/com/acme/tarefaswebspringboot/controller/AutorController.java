package com.acme.tarefaswebspringboot.controller;

import com.acme.tarefaswebspringboot.model.Autor;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/autores")
public class AutorController {

    RestTemplate rest = new RestTemplate();

    @GetMapping
    String index(Model model) {
        List<Autor> autores = rest.getForObject("http://localhost:8888/autores", List.class);
        model.addAttribute("autores", autores);
        return "autores/index";
    }

    @GetMapping("/excluir/{id}")
    String delete(@PathVariable Long id) {
        rest.delete("http://localhost:8888/autores/" + id);
        return "redirect:/autores";
    }

    @GetMapping("/novo")
    String novo(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/form";
    }

    @GetMapping("/alterar/{id}")
    String novo(Model model, @PathVariable Long id) {
        Autor autor = rest.getForObject("http://localhost:8888/autores/" + id, Autor.class);
        model.addAttribute("autor", autor);
        return "autores/form";
    }

    @PostMapping("/salvar")
    String inserir(@Valid Autor autor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "autores/form";
        }
        else {
            val request = new HttpEntity<>(autor);
            if (autor.getId() == null) {
                rest.postForObject("http://localhost:8888/autores", request, Autor.class);
            }
            else {
                rest.put("http://localhost:8888/autores/" + autor.getId(), request, Autor.class);
            }
            return "redirect:/autores";
        }
    }

}

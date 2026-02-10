package com.jonasdurau.usermanagement.controllers;

import com.jonasdurau.usermanagement.dtos.UserDTO;
import com.jonasdurau.usermanagement.entities.User;
import com.jonasdurau.usermanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // --- LISTAGEM (Página Completa) ---
    @GetMapping
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<User> page = service.findAll(pageable);
        model.addAttribute("usersPage", page);
        return "users/list";
    }

    // --- BUSCA HTMX (Apenas Fragmento) ---
    @GetMapping("/search")
    public String search(@RequestParam("query") String query,
                         Model model,
                         @PageableDefault(size = 10) Pageable pageable) {
        Page<User> page = service.search(query, pageable);
        model.addAttribute("usersPage", page);
        
        // Retorna apenas o corpo da tabela para o HTMX substituir
        return "users/list :: users-table-body";
    }

    // --- FORMULÁRIO DE CRIAÇÃO ---
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "users/form";
    }

    // --- SALVAR (POST) ---
    @PostMapping
    public String save(@Valid @ModelAttribute("userDto") UserDTO userDto,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }

        try {
            service.save(userDto);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            // Transforma erro de regra de negócio (Service) em erro de campo (View)
            result.rejectValue("document", "error.userDto", e.getMessage());
            return "users/form";
        }
    }

    // --- FORMULÁRIO DE EDIÇÃO ---
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        User user = service.findById(id);

        // Mapper Entity -> DTO manual para preencher o form
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setBirthDate(user.getBirthDate());
        dto.setDocument(user.getDocument());
        
        // Dados do Endereço (Flattening)
        if (user.getAddress() != null) {
            dto.setAddressLine(user.getAddress().getAddressLine());
            dto.setAddressNumber(user.getAddress().getAddressNumber());
            dto.setCity(user.getAddress().getCity());
            dto.setState(user.getAddress().getState());
            dto.setZip(user.getAddress().getZip());
        }

        model.addAttribute("userDto", dto);
        return "users/form";
    }

    // --- ATUALIZAR (POST) ---
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("userDto") UserDTO userDto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }

        try {
            service.update(id, userDto);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            result.rejectValue("document", "error.userDto", e.getMessage());
            return "users/form";
        }
    }

    // --- DELETAR (HTMX) ---
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return ""; // Retorna vazio para o HTMX remover a linha da tabela
    }
}
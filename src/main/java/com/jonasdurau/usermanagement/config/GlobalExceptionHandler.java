package com.jonasdurau.usermanagement.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Captura erros gerais (500) - NullPointer, Banco fora do ar, etc.
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralError(Exception e, Model model, HttpServletRequest request) {
        logger.error("Unexpected error at {}", request.getRequestURI(), e);
        
        model.addAttribute("error", "An unexpected error occurred.");
        model.addAttribute("message", e.getMessage()); // Cuidado: Em prod, evite expor stack trace
        model.addAttribute("status", 500);
        
        return "error"; // Vai procurar por templates/error.html
    }

    // Captura Recurso Não Encontrado (404)
    // Nota: Para isso funcionar com Spring Boot, precisa de config no application.yml
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException e, Model model) {
        model.addAttribute("error", "Page not found");
        model.addAttribute("message", "The page you are looking for does not exist.");
        model.addAttribute("status", 404);
        
        return "error";
    }
}
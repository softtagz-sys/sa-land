package be.kdg.land.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleServerError(Exception exception, Model model) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        model.addAttribute("errorMessage", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityError(Exception exception, Model model) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        model.addAttribute("errorMessage", exception.getMessage());
        return modelAndView;
    }
}

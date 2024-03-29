package com.example.servingwebcontent.database;

import com.example.servingwebcontent.registration.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Controller
public class SalonyController {
    @Autowired
    private SalonyDAO dao;

    @RequestMapping(value={"/salon"})
    public String showDbPage(Model model) {
        List<Salon> listSalon = dao.findAll();
        model.addAttribute("listSalon", listSalon);

        return "salon/salon";}

    @GetMapping("/new_salon")
    public String showNewForm(Model model) {
        Salon salon = new Salon();
        model.addAttribute("salon",salon);

        return "salon/new_salon";
    }

    @RequestMapping(value = "/save_salon", method = RequestMethod.POST)
    public String save(@ModelAttribute("salon") Salon salon) {
        try {
            dao.save(salon);
        } catch (Exception exception) {
            return "redirect:/database_integrity_error";
        }

        return "redirect:/salon";
    }

    @RequestMapping("/edit_salon/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("salon/edit_salon");
        Salon salon = dao.get(id);
        mav.addObject("salon", salon);

        return mav;
    }

    @RequestMapping(value = "/update_salon", method = RequestMethod.POST)
    public String update(@ModelAttribute("salon") Salon salon) {
        try {
            dao.update(salon);
        } catch (Exception exception) {
            return "redirect:/database_integrity_error";
        }

        return "redirect:/salon";
    }

    @RequestMapping("/delete_salon/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        dao.delete(id);

        return "redirect:/salon";
    }
}


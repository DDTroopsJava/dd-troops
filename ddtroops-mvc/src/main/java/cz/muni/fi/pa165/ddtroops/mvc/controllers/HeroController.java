package cz.muni.fi.pa165.ddtroops.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.facade.HeroFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/heroes")
public class HeroController {

    private final static Logger log = LoggerFactory.getLogger(HeroController.class);

    @Autowired
    private HeroFacade heroFacade;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        log.debug("List all");
        model.addAttribute("heroes", heroFacade.findAll());
        return "heroes/list";
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug(" Read ({})", id);
        model.addAttribute("hero", heroFacade.findById(id));
        return "heroes/read";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin()){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }

        HeroDTO hero = heroFacade.findById(id);
        heroFacade.delete(id);
        log.debug("delete hero({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Hero \"" + hero.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/heroes").build().toUriString();
    }

}


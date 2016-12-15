package cz.muni.fi.pa165.ddtroops.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopUpdateDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.facade.HeroFacade;
import cz.muni.fi.pa165.ddtroops.facade.TroopFacade;
import cz.muni.fi.pa165.ddtroops.mvc.validators.TroopCreateDTOValidator;
import cz.muni.fi.pa165.ddtroops.mvc.validators.TroopUpdateDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/troops")
public class TroopController {
    private final static Logger log = LoggerFactory.getLogger(TroopController.class);

    @Autowired
    TroopFacade troopFacade;

    @Autowired
    HeroFacade heroFacade;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof TroopCreateDTO) {
            binder.addValidators(new TroopCreateDTOValidator());
        }

        if (binder.getTarget() instanceof TroopUpdateDTO) {
            binder.addValidators(new TroopUpdateDTOValidator());
        }
    }


    @RequestMapping(value="", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        log.debug("List all");
        model.addAttribute("troops", troopFacade.findAll());
        return "troops/list";
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug(" Read ({})", id);

        model.addAttribute("troop", troopFacade.findById(id));
        return "troops/read";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin()){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }

        TroopDTO troop = troopFacade.findById(id);
        troopFacade.delete(id);
        log.debug("delete troop({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Troop \"" + troop.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/troops").build().toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTroop(@PathVariable long id, Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder) {

        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin() && logUser.getId() != id){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }

        log.debug("[TROOP] Edit {}", id);
        TroopDTO troopDTO = troopFacade.findById(id);

        model.addAttribute("troopEdit", troopDTO);
        return "/troops/edit";
    }
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id,
        @Valid @ModelAttribute("troopEdit")TroopUpdateDTO formBean,
        BindingResult bindingResult,
        Model model,
        UriComponentsBuilder uriBuilder,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request) {

        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin() && logUser.getId() != id){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }

        formBean.setId(id);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }

            model.addAttribute("troopEdit", formBean);
            return "troops/edit";
        }

        if (troopFacade.findByName(formBean.getName()) != null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Troop with the name " + formBean.getName() + " already exists");
            return "redirect:" + uriBuilder.path("/troops/edit/{id}").buildAndExpand(id).encode().toUriString();
        }
        
        log.debug("[TROOP] Update: {}", formBean);
        TroopDTO result = troopFacade.update(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Troop " + result.getName() + " was updated");
        return "redirect:" + uriBuilder.path("/troops/read/{id}").buildAndExpand(id).encode().toUriString();
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createTroop(Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder) {

        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");
        if(!logUser.isAdmin()){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }
        
        log.debug("[TROOP] Create {}");
        model.addAttribute("troopCreate", new TroopCreateDTO());

        return "/troops/create";
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
        @Valid @ModelAttribute("troopCreate")TroopCreateDTO formBean,
        BindingResult bindingResult,
        Model model, 
        RedirectAttributes redirectAttributes, 
        UriComponentsBuilder uriBuilder, 
        HttpServletRequest request) {

        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin()){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            model.addAttribute("troopCreate", formBean);
            return "/troops/create";
        }

        if (troopFacade.findByName(formBean.getName()) != null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Troop with the name " + formBean.getName() + " already exists");
            return "redirect:" + uriBuilder.path("/troops/create").build().toUriString();
        }
        
        log.debug("[TROOP] Create: {}", formBean);
        TroopDTO result = troopFacade.create(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Troop " + result.getName() + " was created");
        return "redirect:" + uriBuilder.path("/troops/read/" + result.getId()).buildAndExpand(result.getId()).encode().toUriString();
    }
    
    @RequestMapping(value = "/battle", method = RequestMethod.GET)
    public String prepareTroops(Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder) {

        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");
        if(!logUser.isAdmin()){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }
        
        log.debug("[TROOP] Battle {}");
        model.addAttribute("troops", troopFacade.findAll());
        return "/troops/battle";
    }
    
    @RequestMapping(value = "/battle", method = RequestMethod.POST)
    public String battleTroops(Model model, 
            HttpServletRequest request, 
            RedirectAttributes redirectAttributes, 
            UriComponentsBuilder uriBuilder) {

        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");
        if(!logUser.isAdmin()){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }
        log.debug("[TROOP] Battle {}");
        
        Long firstId = Long.parseLong(request.getParameter("firstTroopId"));
        Long secondId = Long.parseLong(request.getParameter("secondTroopId"));
        
        if (firstId.equals(secondId)) {
            log.debug("[TROOP] Battle {same troops, redirecting with error}");
            redirectAttributes.addFlashAttribute("alert_warning", "A troop can't fight against itself!");
            return "redirect:" + uriBuilder.path("/troops/battle").build().toUriString();
        }
        
        log.debug("1st ID = " + firstId + ", 2nd ID = " + secondId);
        
        TroopDTO winner = troopFacade.battle(troopFacade.findById(firstId), troopFacade.findById(secondId));
        
        if (winner == null)
            redirectAttributes.addFlashAttribute("alert_success", "DRAW! The troops are evenly matched!");
        else 
            redirectAttributes.addFlashAttribute("alert_success", "Troop " + winner.getName() + " has WON");
        
        return "redirect:" + uriBuilder.path("/troops").build().toUriString();
    }
    
    @RequestMapping(value = "/addhero/{id}", method = RequestMethod.GET)
    public String addHero(@PathVariable long id, Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder) {

        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin() && logUser.getId() != id){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }

        log.debug("[TROOP] Add Hero {}", id);
        TroopDTO troopDTO = troopFacade.findById(id);

        model.addAttribute("troop", troopDTO);
        model.addAttribute("heroes", heroFacade.findAll().stream().filter(hero -> hero.getTroop() == null).toArray());
        
        return "/troops/addhero";
    }
     
    @RequestMapping(value="/addhero/{id}", method = RequestMethod.POST)
    public String addHeroToTroop(@PathVariable long id,
            Model model, 
            HttpServletRequest request, 
            RedirectAttributes redirectAttributes, 
            UriComponentsBuilder uriBuilder) {

        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin() && logUser.getId() != id){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }
        
        TroopDTO editedTroop = troopFacade.findById(id);

        TroopUpdateDTO updatedTroop = new TroopUpdateDTO();
        updatedTroop.setId(id);
        updatedTroop.setName(editedTroop.getName());
        updatedTroop.setMission(editedTroop.getMission());
        updatedTroop.setGold(editedTroop.getGold());
        updatedTroop.setHeroes(editedTroop.getHeroes());
        
        log.debug("[TROOP] Add Hero: {}", editedTroop);
        Long heroId = Long.parseLong(request.getParameter("heroId"));
        HeroDTO addedHero = heroFacade.findById(heroId);
        // add the selected hero to the troop
        editedTroop.getHeroes().add(addedHero);
        
        // save the troop to DB
        TroopDTO result = troopFacade.update(updatedTroop);


        redirectAttributes.addFlashAttribute("alert_success", "Troop " + result.getName() + " was updated: Hero " + addedHero.getName() + " was succesfully added.");
        return "redirect:" + uriBuilder.path("/troops/addhero/{id}").buildAndExpand(id).encode().toUriString();
    }
    
    @RequestMapping(value="/removehero/{id}/{heroId}", method = RequestMethod.POST)
    public String removeHeroFromTroop(@PathVariable long id, @PathVariable long heroId,
            Model model, 
            HttpServletRequest request, 
            RedirectAttributes redirectAttributes, 
            UriComponentsBuilder uriBuilder) {
        
        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin() && logUser.getId() != id){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }
        
        TroopDTO editedTroop = troopFacade.findById(id);
        
        log.debug("[TROOP] Remove Hero: {}", heroId);
        HeroDTO removedHero = heroFacade.findById(heroId);
        // save the troop to DB
        TroopDTO result = troopFacade.removeHero(editedTroop, removedHero);
        
        redirectAttributes.addFlashAttribute("alert_success", "Troop " + result.getName() + " was updated: Hero " + removedHero.getName() + " was succesfully removed.");
        return "redirect:" + uriBuilder.path("/troops/addhero/{id}").buildAndExpand(id).encode().toUriString();
    }
}

package cz.muni.fi.pa165.ddtroops.mvc.controllers;

import javax.validation.Valid;

import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserUpdateDTO;
import cz.muni.fi.pa165.ddtroops.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by pstanko.
 * @author pstanko
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("[USERS] List all");
        model.addAttribute("users", userFacade.findAll());
        return "user/list";
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable long id, Model model) {
        log.debug("[USERS] Read ({})", id);
        model.addAttribute("user", userFacade.findById(id));
        return "user/read";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        log.info("[USERS] Delete for {}", id);
        UserDTO user = userFacade.findById(id);
        log.info("[USERS] Delete found user {}", user.getName());
        userFacade.delete(id);
        log.debug("delete user({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "User \"" + user.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/user/").build().toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable long id, Model model) {
        log.debug("[USER] Edit {}", id);
        UserDTO userDTO = userFacade.findById(id);

        model.addAttribute("userEdit", userDTO);
        return "/user/edit";
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id,
        @Valid @ModelAttribute("userEdit")UserUpdateDTO formBean,
        BindingResult bindingResult,
        Model model,
        UriComponentsBuilder uriBuilder,
        RedirectAttributes redirectAttributes) {

        formBean.setId(id);
        log.debug("[USER] Update: {}", formBean);
        UserDTO result = userFacade.update(formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "redirect:" + uriBuilder.path("/user/edit/{id}").buildAndExpand(id).encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", "User " + result.getEmail() + " was updated");
        return "redirect:" + uriBuilder.path("/user/read/{id}").buildAndExpand(id).encode().toUriString();
    }

}

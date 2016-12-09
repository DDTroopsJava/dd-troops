package cz.muni.fi.pa165.ddtroops.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.facade.RoleFacade;
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

/**
 * Created by pstanko.
 * @author pstanko
 */
@Controller
@RequestMapping("/roles")
public class RoleController {
    private final static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleFacade roleFacade;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        log.debug("List all");
        model.addAttribute("roles", roleFacade.findAll());
        return "roles/list";
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug(" Read ({})", id);

        model.addAttribute("role", roleFacade.findById(id));
        return "roles/read";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        UserDTO logUser = (UserDTO) request.getSession().getAttribute("user");

        if(!logUser.isAdmin()){
            return "redirect:" + uriBuilder.path("/").build().toUriString();
        }

        RoleDTO role = roleFacade.findById(id);
        roleFacade.delete(id);
        log.debug("delete role({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Role \"" + role.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/roles").build().toUriString();
    }
}

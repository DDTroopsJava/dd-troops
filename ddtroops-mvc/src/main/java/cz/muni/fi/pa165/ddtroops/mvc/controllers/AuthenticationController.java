package cz.muni.fi.pa165.ddtroops.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.muni.fi.pa165.ddtroops.dto.UserCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserLoginDTO;
import cz.muni.fi.pa165.ddtroops.facade.UserFacade;
import cz.muni.fi.pa165.ddtroops.mvc.validators.UserCreateDTOValidator;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by pstanko.
 * @author pstanko
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserFacade userFacade;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof UserCreateDTO) {
            binder.addValidators(new UserCreateDTOValidator());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String loginUser(Model model, HttpServletRequest request) {

        if(request.getSession().getAttribute("user") != null){
            return "home";
        }

        log.debug("[AUTH] Login");

        model.addAttribute("userLogin", new UserLoginDTO());
        return "/auth/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser(Model model, HttpServletRequest request) {
        log.debug("[AUTH] Login");
        request.getSession().removeAttribute("user");
        return "home";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerUser(Model model, HttpServletRequest request) {
        log.debug("[AUTH] Register");
        model.addAttribute("userRegister", new UserCreateDTO());
        return "auth/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("userRegister") UserCreateDTO formBean, BindingResult bindingResult,
        Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug("register(userRegister={})", formBean);


        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.debug("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.debug("FieldError: {}", fe);
            }

            model.addAttribute("userRegister", formBean);
            return "/auth/register";
        }

        if(userFacade.findByEmail(formBean.getEmail()) != null){
            redirectAttributes.addFlashAttribute("alert_warning", "Email " + formBean.getEmail() + " already exists!");
            return "redirect:" + uriBuilder.path("/auth/register").build().toUriString();
        }

        UserDTO user = userFacade.register(formBean);
        request.getSession().setAttribute("user", user);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Register " + formBean.getEmail() + " succeeded");

        return "redirect:" + uriBuilder.path("/").build().toUriString();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute("userLogin") UserLoginDTO formBean, BindingResult bindingResult,
        Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug("login(userLogin={})", formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.debug("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.debug("FieldError: {}", fe);
            }
            model.addAttribute("userLogin", new UserLoginDTO());
            return "/auth/login";
        }

        UserDTO matchingUser = userFacade.findByEmail(formBean.getEmail());
        if(matchingUser==null) {
            log.warn("no user with email {}", formBean.getEmail());
            redirectAttributes.addFlashAttribute("alert_warning", "No user with email: " + formBean.getEmail());
            return "redirect:" + uriBuilder.path("/auth").build().toUriString();
        }

        if (!userFacade.authenticate(formBean.getEmail(), formBean.getPassword())) {
            log.warn("wrong credentials: user={} password={}", formBean.getEmail(), formBean.getPassword());
            redirectAttributes.addFlashAttribute("alert_warning", "Login " + formBean.getEmail() + " failed ");

            return "redirect:" + uriBuilder.path("/auth").build().toUriString();
        }
        request.getSession().setAttribute("user", matchingUser);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Login " + formBean.getEmail() + " succeeded ");
        return "redirect:" + uriBuilder.path("/heroes").build().toUriString();
    }
}

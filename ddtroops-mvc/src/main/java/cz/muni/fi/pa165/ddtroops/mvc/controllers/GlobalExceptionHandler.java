package cz.muni.fi.pa165.ddtroops.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by pstanko.
 * @author pstanko
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Throwable.class)
    public ModelAndView fatalError(Exception ex) {
        log.error("FATAL: ", ex);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("exception/fatal");
        return mav;
    }
}

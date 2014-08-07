package demo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.models.User;

/**
 * Controllers que utilizam o usuário na sessão
 */
@Controller
@SessionAttributes("user")
public abstract class AbstractController {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @ModelAttribute("user")
    public User createUser() {
        // cria usuário na sessão
        User user = new User();
        LOG.debug("Session: {}", user);
        return user;
    }

    protected void addMessageToView(String message, RedirectAttributes redirect) {
        // publica alertas nas views
        LOG.debug("globalMessage={}", message);
        redirect.addFlashAttribute("globalMessage", message);
    }

    @ExceptionHandler
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        // tratamento comum de erros inesperados
        LOG.error("Request error: {}", req.getRequestURL(), exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        return mav;
    }

}

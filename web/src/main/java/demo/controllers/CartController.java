package demo.controllers;

import static demo.web.util.CsvUtil.toCSV;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import demo.models.Product;
import demo.models.User;

/**
 * Controller do carrinho do cliente
 */
@Controller
@SessionAttributes("user")
public class CartController extends AbstractController {

    @RequestMapping(value = "/listCart", method = RequestMethod.GET)
    public ModelAndView listCart(@ModelAttribute User user) {
        LOG.debug("listar produtos");

        return new ModelAndView("produtos/cart", "cart", user.getCart());
    }

    @RequestMapping("/addToCart/{id}")
    public ModelAndView addToCart(@PathVariable("id") Product product, @ModelAttribute User user, BindingResult result,
            RedirectAttributes redirect, HttpServletRequest request) {
        user.setId(WebUtils.getSessionId(request));
        user.add(product);

        addMessageToView("Added " + product, redirect);
        return listCart(user);
    }

    @RequestMapping("/removeFromCart/{id}")
    public ModelAndView removeFromCart(@PathVariable("id") Product product, @ModelAttribute User user,
            BindingResult result, RedirectAttributes redirect) {
        user.remove(product);

        addMessageToView("Removed " + product, redirect);
        return listCart(user);
    }

    @RequestMapping(value = "/submitCart", produces = "text/plain;charset=UTF-8")
    public @ResponseBody String submitCart(@ModelAttribute User user, HttpServletResponse response) throws IOException {
        Product[] clear = user.clear();
        if (clear == null || clear.length == 0) {
            response.sendRedirect("/");
            return "";
        }
        response.setHeader("Content-Disposition", "attachment; filename=cart.csv");
        return toCSV(clear);
    }

}

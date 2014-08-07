package demo.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import demo.models.Product;
import demo.models.User;
import demo.web.service.ProductService;

/**
 * Controller administrativo de produtos
 */
@Controller
@RequestMapping("/admin")
@SessionAttributes("user")
public class AdminController extends AbstractController {

    private static final String ADMIN_VIEW = "produtos/admin";

    @Autowired
    protected ProductService productService;

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void init() {
        // recarrega os dados de produtos
        productService.setUp(WebUtils.getTempDir(servletContext));
    }

    @RequestMapping
    public ModelAndView admin() {
        return new ModelAndView(ADMIN_VIEW);
    }

    @RequestMapping("/sync")
    public String sync(@ModelAttribute User user, RedirectAttributes attr) {
        int result = productService.sync(user);
        addMessageToView("Total: " + result, attr);
        return ADMIN_VIEW;
    }

    @RequestMapping("/clear")
    public String clear(@ModelAttribute User user, RedirectAttributes attr) {
        int result = productService.clear(user);
        addMessageToView("Total: " + result, attr);
        return ADMIN_VIEW;
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") Product product, @ModelAttribute User user, RedirectAttributes redirect) {
        productService.delete(user, product);
        addMessageToView("Item removido: " + product.getId(), redirect);
        return ADMIN_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Product product, @ModelAttribute User user, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("produtos/form", "formErrors", result.getAllErrors());
        }
        product = productService.create(user, product);
        addMessageToView("Item criado: " + product.getId(), redirect);
        return new ModelAndView("redirect:/{product.id}", "product.id", product.getId());
    }

}

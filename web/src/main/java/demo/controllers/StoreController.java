package demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import demo.models.Product;
import demo.web.service.ProductService;

/**
 * Controller das views de consulta de produtos (templates.produtos)
 */
@Controller
@RequestMapping("/")
@SessionAttributes("user")
public class StoreController extends AbstractController {

    @Autowired
    protected ProductService productService;

    @RequestMapping
    public ModelAndView list() {
        LOG.debug("listar produtos");

        Iterable<Product> products = productService.findAll();
        return new ModelAndView("produtos/list", "products", products);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Product product) {
        LOG.debug("visualizar produto {}", product);
        // o productIdConverter busca o produto para o model
        return new ModelAndView("produtos/view", "product", product);
    }

    @RequestMapping(value="/form", params = "new", method = RequestMethod.GET)
    public String edit(@ModelAttribute Product product) {
        LOG.debug("editar produto {}", product);
        // o productIdConverter busca o produto para o model
        return "produtos/form";
    }

}

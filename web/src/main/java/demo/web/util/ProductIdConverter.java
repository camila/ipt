package demo.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import demo.models.Product;
import demo.web.service.ProductService;

/**
 * Utilitário de conversão de valores das views para objetos dos controllers.
 */
@Component
public class ProductIdConverter implements Converter<Object, Product> {

    @Autowired
    private ProductService productService;

    @Override
    public Product convert(Object id) {
        return productService.findById(Integer.valueOf(id.toString()));
    }

}

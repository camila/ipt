package demo.web.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Product;
import demo.models.User;
import demo.web.repository.ProductRepository;

/**
 * Serviço responsável por consultar o(s) repositório(s) de dados de produtos.
 */
@Service
public class ProductService extends AbstractService {

    @Autowired
    private ProductRepository productRepository;

    public Product findById(Integer id) {
        return productRepository.find(id);
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public Product create(User admin, Product product) {
        authorize(admin);
        return productRepository.saveOrUpdate(product);
    }

    public boolean delete(User admin, Product product) {
        authorize(admin);
        return productRepository.delete(product);
    }

    public int sync(User admin) {
        authorize(admin);
        return productRepository.sync();
    }

    public int clear(User admin) {
        authorize(admin);
        admin.clear();
        return productRepository.deleteAll();
    }

    public void setUp(File tempDir) {
        if (tempDir != null && tempDir.isDirectory()) {
            productRepository.initDatastore(tempDir);
        }
    }

}

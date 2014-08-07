package demo.web.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo.models.Product;

/**
 * Repositório de produtos, delega ao repositório de dados e mantém um cache dos produtos disponíveis em memória
 */
@Repository
public class ProductRepository {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final ConcurrentMap<Integer, Product> cache = new ConcurrentHashMap<Integer, Product>();

    @Autowired
    private CsvDatastore dataStore;

    public Iterable<Product> findAll() {
        LOG.debug("findAll");
        List<Product> values = new ArrayList<Product>(cache.values());
        Collections.sort(values);
        return values;
    }

    public Product saveOrUpdate(Product p) {
        LOG.debug("saveOrUpdate {}", p);
        Product fromDatastore = dataStore.saveOrUpdate(p);
        cache.put(fromDatastore.getId(), fromDatastore);
        return fromDatastore;
    }

    public Product find(Integer id) {
        LOG.debug("find {}", id);
        return cache.get(id);
    }

    public boolean delete(Product p) {
        LOG.debug("delete {}", p);
        return cache.remove(p.getId(), p);
    }

    public synchronized int deleteAll() {
        LOG.debug("deleteAll");
        int size = cache.size();
        cache.clear();
        dataStore.delete();
        return size;
    }

    public synchronized int sync() {
        LOG.debug("sync");
        cache.putAll(dataStore.selectAll());
        return cache.size();
    }

    public void initDatastore(File tempDir) {
        LOG.debug("init {}", tempDir);
        dataStore.initDatastore(tempDir);
        sync();
    }

}

package demo.web.repository;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import demo.models.Product;
import demo.web.util.CsvUtil;

/**
 * Implementação de repositório que armazena os dados em um CSV no diretório temporário do servidor
 */
@Component("dataStore")
public class CsvDatastore {

    private static final Logger LOG = LoggerFactory.getLogger(CsvDatastore.class);

    private static final int MAX_ENTRIES = Integer.getInteger("csv.max.entries", 100);

    private AtomicInteger sequence;

    private File csv;

    @PostConstruct
    public void init() {
        sequence = new AtomicInteger();
    }

    public Map<Integer, Product> selectAll() {
        Map<Integer, Product> data = new TreeMap<Integer, Product>();
        try {
            validate(csv);
            Product product = null;
            String[] allLines = CsvUtil.readLines(csv);
            for (String productCsv : allLines) {
                product = CsvUtil.fromCSV(productCsv);
                data.put(product.getId(), product);
            }
            if (product != null) {
                resetSequence(product.getId());
            }
        } catch (Exception e) {
            LOG.error("selectAll", e);
        }
        return data;
    }

    public void delete() {
        LOG.warn("delete {}", csv);
        try {
            validate(csv);
            csv.delete();
        } catch (Exception e) {
            LOG.error("save(data.csv)", e);
        }
    }

    public synchronized Product saveOrUpdate(Product p) {
        try {
            validate(csv);
            int id = p.getId();
            if (id <= 0) {
                p.setId(nextId());
            }
            CsvUtil.writeLine(csv, p);
            LOG.warn("saveOrUpdate: {}", p);
        } catch (Exception e) {
            LOG.error("saveOrUpdate: " + p, e);
        }
        return p;
    }

    public void validate(File csv) throws IOException {
        if (csv == null) {
            throw new IllegalStateException("Invalid datastore " + csv);
        }
        if (!csv.exists()) {
            csv.createNewFile();
        }
    }

    public void initDatastore(File tempDir) {
        try {
            csv = CsvUtil.createFile(tempDir);
            LOG.warn("initDatastore {}", csv);
        } catch (Exception e) {
            LOG.error("initDatastore " + tempDir, e);
            csv = null;
        }
    }

    private int nextId() {
        int nextId = sequence.incrementAndGet();
        if (MAX_ENTRIES > 0 && nextId > MAX_ENTRIES) {
            throw new IllegalStateException("Limit reached: " + nextId);
        }
        LOG.debug("nextId={}", nextId);
        return nextId;
    }

    public void resetSequence(int nextId) {
        sequence.set(nextId);
    }

}

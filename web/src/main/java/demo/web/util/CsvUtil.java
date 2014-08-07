package demo.web.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import demo.models.Product;
import demo.models.Product.Image;

/**
 * Utilitário para gravação de produtos em CSV
 */
public class CsvUtil {

    public static final String DATA_CSV = System.getProperty("csv.file.name", "data.csv");

    public static final String SEPARATOR = ";";

    public static Product fromCSV(String csv) {
        String[] productLine = csv.split(SEPARATOR);
        Product p = new Product();
        p.setId(Integer.parseInt(productLine[0]));
        p.setSummary(productLine[1]);
        p.setValue(productLine[2]);
        p.setImage(Image.valueOf(productLine[3]));
        return p;
    }

    public static String toCSV(Product[] products) {
        StringBuilder csv = new StringBuilder();
        for (Product product : products) {
            csv.append(toCSV(product));
        }
        return csv.toString();
    }

    public static String toCSV(Product p) {
        return p.getId() + SEPARATOR + p.getSummary() + SEPARATOR + p.getValue() + SEPARATOR + p.getImage().ordinal()
                + "\n";
    }

    public static String[] readLines(File csv) throws IOException {
        List<String> csvLines = new ArrayList<String>();
        List<String> lines = Files.readAllLines(csv.toPath(), StandardCharsets.UTF_8);
        if (lines.isEmpty()) {
            long copy = Files.copy(getDefaultStore(), csv.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (copy > 0) {
                lines = Files.readAllLines(csv.toPath(), StandardCharsets.UTF_8);
            }
        }
        for (String line : lines) {
            if (isCSV(line)) {
                csvLines.add(line);
            }
        }
        return csvLines.toArray(new String[csvLines.size()]);
    }

    public static void writeLine(File csv, Product p) throws IOException {
        Files.write(csv.toPath(), toCSV(p).getBytes());
    }

    public static File createFile(File tempDir) throws IOException {
        if (tempDir == null || !tempDir.isDirectory()) {
            return null;
        }
        File file = new File(tempDir, DATA_CSV);
        if (!file.exists()) {
            Files.copy(getDefaultStore(), file.toPath());
        }
        return file;
    }

    private static boolean isCSV(String productCsv) {
        return !productCsv.trim().isEmpty() && productCsv.split(SEPARATOR).length >= 4;
    }

    public static InputStream getDefaultStore() {
        return CsvUtil.class.getResourceAsStream("/" + DATA_CSV);
    }

}
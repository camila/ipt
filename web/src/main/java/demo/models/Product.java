package demo.models;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Product implements Comparable<Product> {

    /** imagens pre-definidas de produtos */
    public static enum Image {
        img, a, b, c, d, e;

        public String getPath() {
            return "img/" + name() + ".jpg";
        }
    }

    private int id;

    private Image image;

    @NotEmpty(message = "{product.summary.empty}")
    private String summary;

    @NotNull(message = "{product.value.null}")
    private String value;

    public Product() {
        image = Image.img;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return super.toString() + " [id: " + id + " | image: " + image + " | summary: " + summary + " | value: "
                + value + "]";
    }

    @Override
    public int compareTo(Product o) {
        return o == null ? -1 : ((Integer) id).compareTo((Integer) o.getId());
    }

}

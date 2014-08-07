package demo.models;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class User {

    /** perfil do usuário */
    public static enum Type {
        USER, ADMIN;
    }

    private int id;

    private Type type;

    private Set<Product> cart;

    public User() {
        cart = new TreeSet<Product>();
        type = Type.USER;
    }

    public boolean add(Product product) {
        return cart.add(product);
    }

    public boolean remove(Product product) {
        return cart.remove(product);
    }
    
    public Product[] clear() {
        Product[] cartContents = cart.toArray(new Product[cart.size()]);
        cart.clear();
        return cartContents;
    }

    public Set<Product> getCart() {
        return Collections.unmodifiableSet(cart);
    }

    public void setCart(Set<Product> cart) {
        this.cart = cart;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        if (type != null) {
            this.type = type;
        }
    }

    public void setId(String sessionId) {
        if (sessionId != null) {
            this.id = sessionId.hashCode();
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return super.toString() + " [id: " + id + " | type: " + type + " | cart: " + cart + "]";
    }

}

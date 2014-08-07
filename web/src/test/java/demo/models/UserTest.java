package demo.models;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import demo.models.Product;
import demo.models.User;

public class UserTest {

    private User user;

    private Product[] productFixture = { new Product(), new Product(), new Product() };

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void testCart() {
        productFixture[0].setId(1);
        productFixture[1].setId(2);
        productFixture[2].setId(3);

        user.add(productFixture[2]);
        user.add(productFixture[0]);
        user.add(productFixture[1]);

        assertArrayEquals(productFixture, user.getCart().toArray(new Product[3]));
    }

}

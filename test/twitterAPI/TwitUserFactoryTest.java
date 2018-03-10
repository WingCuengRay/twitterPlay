package lyc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TwitUserFactoryTest {
    private TwitUserFactory u_factory;
    @Before
    public void setUp() {
        u_factory = TwitUserFactory.getInstance();
        assertNotNull(u_factory);
    }

    @Test
    public void TestSingleton(){
        TwitUserFactory f1 = TwitUserFactory.getInstance();
        TwitUserFactory f2 = TwitUserFactory.getInstance();
        assertEquals(f1, f2);
    }


    @Test
    public void TestGetOrCreateUser(){
        UserBase u1 = u_factory.getOrCreateUser(123, "hello123", "hello123");
        UserBase u4 = u_factory.getOrCreateUser(456, "hello456", "hello456");
        UserBase u7 = u_factory.getOrCreateUser(789, "hello789", "hello789");

        UserBase u11 = u_factory.getOrCreateUser(123, "hello123", "hello123");
        UserBase u44 = u_factory.getOrCreateUser(456, "hello456", "hello456");
        UserBase u77 = u_factory.getOrCreateUser(789, "hello789", "hello789");

        assertEquals(u1, u11);
        assertEquals(u4, u44);
        assertEquals(u7, u77);
    }

    @Test
    public void TestGetUserById(){
        UserBase u1 = u_factory.getOrCreateUser(123, "hello123", "hello123");
        UserBase u11 = u_factory.getUserById(123);

        UserBase u_invalid = u_factory.getUserById(123456789);

        assertEquals(u1, u11);
        assertNull(u_invalid);
    }

}
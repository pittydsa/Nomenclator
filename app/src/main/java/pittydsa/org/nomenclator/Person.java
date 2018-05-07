package pittydsa.org.nomenclator;

/**
 * POJO representing one person, with name and phone number.
 * Created by toor on 12/19/17.
 */
public class Person {
    /**
     * This is the place where MainActivity uses for people storage
     */
    public static Person[] people = null;

    /**
     * Now not needed, have api working
     */
    public static void init() {

    }

    private String name        = "";
    private String phoneNumber = "";

    public Person() {
    }

    public Person(String n, String phone) {
        name = n;
        phoneNumber = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return name.substring(0, 4) + phoneNumber;
    }
}

package pittydsa.org.nomenclator;

/**
 * Created by toor on 12/19/17.
 */

public class Person {
    public static Person[] people = new Person[5];
    public static void init() {
        people[0] = new Person("David Ankin", "+12679925122");
        people[1] = new Person("David Ankin", "+12679925122");
        people[2] = new Person("David Ankin", "+12679925122");
        people[3] = new Person("David Ankin", "+12679925122");
        people[4] = new Person("David Ankin", "+12679925122");
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
}

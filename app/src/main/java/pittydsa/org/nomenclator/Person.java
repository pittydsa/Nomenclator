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
     * Tested on AVD, the 30 person scrolling thing works. For production this
     * should probably initialize an empty array to prevent null pointer.
     */
    public static void init() {
        people = new Person[2];
        people[0] = new Person("David Ankin", "+12679925122");
        people[1] = new Person("David Ankin", "+12679925122");

        // people[15] = new Person("David Ankin", "+12679925122");
        // people[16] = new Person("David Ankin", "+12679925122");
        // people[17] = new Person("David Ankin", "+12679925122");
        // people[18] = new Person("David Ankin", "+12679925122");
        // people[19] = new Person("David Ankin", "+12679925122");
        // people[20] = new Person("David Ankin", "+12679925122");
        // people[21] = new Person("David Ankin", "+12679925122");
        // people[22] = new Person("David Ankin", "+12679925122");
        // people[23] = new Person("David Ankin", "+12679925122");
        // people[24] = new Person("David Ankin", "+12679925122");
        // people[25] = new Person("David Ankin", "+12679925122");
        // people[26] = new Person("David Ankin", "+12679925122");
        // people[27] = new Person("David Ankin", "+12679925122");
        // people[28] = new Person("David Ankin", "+12679925122");
        // people[29] = new Person("David Ankin", "+12679925122");
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

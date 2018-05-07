package pittydsa.org.nomenclator;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Created by cscadmin on 5/6/2018.
 */

public class Configuration implements Serializable {
    public static final long serialVersionUID = 42L;

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        String json = Configuration.gson.toJson(this);
        out.write(json.getBytes(Charset.forName("ASCII")));
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        byte buf[] = new byte[in.available()];
        in.readFully(buf);
        String json = new String(buf, Charset.forName("ASCII"));
        Configuration c = gson.fromJson(json, Configuration.class);
        item = c.getItem();
        status = c.getStatus();
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new ObjectStreamException() {
        };
    }

    private String status;
    private Configuration.Item item;

    public String getStatus() {
        return status;
    }

    public Configuration.Item getItem() {
        return item;
    }

    public static class Item {
        private String message;
        private Item.Person people[];

        @Override
        public String toString() {
            return "Saying message '" + message + "' to " + people.length + " people";
        }

        public String getMessage() {
            return message;
        }

        public Item.Person[] getPeople() {
            return people;
        }

        public static class Person {
            private String name;
            private String phone;

            public String getName() {
                return name;
            }

            public String getPhone() {
                return phone;
            }
        }
    }

    public static void main(String args[]) {
        String testString = "{\"status\":\"ok\",\"item\":{\"message\":\"hello nope\",\"people\":[{\"name\":\"Sona\",\"phone\":\"+12679925122\"},{\"name\":\"Cara\",\"phone\":\"+14123433434\"}]}}";

        System.out.println("hello world");
        Gson gson = new Gson();
        Configuration c = gson.fromJson(testString, Configuration.class);
        System.out.println(c.getStatus());
        System.out.println(c.getItem().toString());
        Configuration.Item.Person[] people = c.getItem().getPeople();
        for (Configuration.Item.Person p :
                people) {
            System.out.println(p.toString());
        }
    }

    public static final Gson gson = new Gson();

    public static Configuration parseResponse(String response) {
        return gson.fromJson(response, Configuration.class);
    }
}

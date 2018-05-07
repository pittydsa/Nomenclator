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
        message1 = c.getMessage1();
        message2 = c.getMessage2();
        people = c.getPeople();
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new ObjectStreamException() {
        };
    }

    private String message1;
    private String message2;
    private Configuration.Person people[];

    public String getMessage1() {
        return message1;
    }
    public String getMessage2() {
        return message2;
    }
    public Configuration.Person[] getPeople() { return people; }

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

    public static void main(String args[]) {
        String testString = "{\"status\":\"ok\",\"item\":{\"message1\":\"Hi, \",\"message2\":\"! This is a test.\",\"people\":[{\"name\":\"a\",\"phone\":\"+12679925122\"},{\"name\":\"b\",\"phone\":\"+12679925122\"},{\"name\":\"c\",\"phone\":\"+12679925122\"},{\"name\":\"d\",\"phone\":\"+12679925122\"},{\"name\":\"a\",\"phone\":\"+12679925122\"},{\"name\":\"b\",\"phone\":\"+12679925122\"},{\"name\":\"c\",\"phone\":\"+12679925122\"},{\"name\":\"d\",\"phone\":\"+12679925122\"},{\"name\":\"a\",\"phone\":\"+12679925122\"},{\"name\":\"b\",\"phone\":\"+12679925122\"},{\"name\":\"c\",\"phone\":\"+12679925122\"},{\"name\":\"d\",\"phone\":\"+12679925122\"}],\"password\":\"b9198f6070\"}}";


        Configuration c = Configuration.parseResponse(testString).getItem();
        System.out.println(Configuration.parseResponse(testString).getStatus());
        System.out.println(c.toString());
        Configuration.Person[] people = c.getPeople();
        for (Configuration.Person p :
                people) {
            System.out.println(p.toString());
        }
    }

    public static final Gson gson = new Gson();

    public static Response parseResponse(String response) {
        return gson.fromJson(response, Response.class);
    }
}

class Response {
    private String status;
    private Configuration item;

    public String getStatus() {
        return status;
    }

    public Configuration getItem() {
        return item;
    }
}
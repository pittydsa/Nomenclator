package pittydsa.org.nomenclator;

/**
 * Created by cscadmin on 12/20/2017.
 */

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang3.StringUtils;

public class MessageFormatter {
    private static final String SEPARATOR = "%";
    private String template;
    private MessageFormatter () {

    }

    public MessageFormatter(String messageTemplate) {
        template = messageTemplate;
    }

    public String getMessage(Object person) {
        boolean previousReplacement = false;
        String[] parts = template.split(SEPARATOR);
        for (int index = 0; index < parts.length; index++) {
            String part = parts[index];
            String methodName = "get" + StringUtils.capitalize(part);
            String result = null;
            java.lang.reflect.Method method;
            try {
                method = person.getClass().getMethod(methodName);
                try {
                    result = (String) method.invoke(person);
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {}
            } catch (SecurityException | NoSuchMethodException e) {}

            if (result == null) {
                System.out.println(index + "\t" + previousReplacement + "\t" + part);
                if (index != 0 && previousReplacement) {
                    // put it back!!!
                    parts[index] = SEPARATOR + part;
                    previousReplacement = false;
                }
                continue;
            }

            // result is whatever we were trying to substitute in instead.
            parts[index] = result;
            previousReplacement = true;
        }
        return StringUtils.join(parts);
    }
}

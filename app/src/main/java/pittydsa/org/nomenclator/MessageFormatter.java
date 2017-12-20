package pittydsa.org.nomenclator;

/**
 * Created by cscadmin on 12/20/2017.
 */

import android.support.annotation.NonNull;

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
        char[] message = template.toCharArray();
        boolean inside = false;
        int start = 0;
        char separator = SEPARATOR.charAt(0);
        int substitutions = 0;
        for (int index = 0; index < message.length; index++) {
            if (!inside && message[index] == separator) {
                inside = true;
                start = index;
                System.out.println("started inside at " + start);
                continue;
            }

            if (inside && message[index] == separator) {
                inside = false;
                String captured = template.substring(start + 1 - 2*(substitutions), index - 2*(substitutions));
                System.out.println(captured + "{}" + template);
                String newValue = tryGetField(captured, person);
                if (newValue != null) {
                    template = splice(newValue, start - 2*(substitutions), index + 1 - 2*(substitutions), template);
                    substitutions += 1;
                }
            }
        }
        return template;
    }

    @NonNull
    private String splice(String input, int start, int end, String original) {
        return original.substring(0, start) + input + original.substring(end, original.length());
    }

    private String tryGetField(String field, Object object) {
        String methodName = "get" + StringUtils.capitalize(field);
        String result = null;
        java.lang.reflect.Method method;
        try {
            method = object.getClass().getMethod(methodName);
            try {
                result = (String) method.invoke(object);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {}
        } catch (SecurityException | NoSuchMethodException e) {}

        return result;
    }
}

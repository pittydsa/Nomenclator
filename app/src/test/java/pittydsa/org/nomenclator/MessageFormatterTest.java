package pittydsa.org.nomenclator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MessageFormatterTest {
    private class SampleClass {
        public String getUser() {
            return "John";
        }
    }
    @Test
    public void substitutionWorks() throws Exception {
        SampleClass sampleClass = new SampleClass();
        MessageFormatter messageFormatter = new MessageFormatter("i am a message template here me roar, %user%! i will never stop, %user,%, %user%, despite my typos.");
        String result = messageFormatter.getMessage(sampleClass);
        assertEquals("i am a message template here me roar, John! i will never stop, %user,%, John, despite my typos.", result);
        assertEquals(4, 2 + 2);
    }


}
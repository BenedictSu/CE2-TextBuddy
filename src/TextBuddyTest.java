import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class TextBuddyTest {

    @Test
    public void testAdd() throws IOException{
        try {
            assertEquals("added to test.txt: \"little brown fox\"", TextBuddy.add("add little brown fox"));
        } catch (IOException e) {
            throw e;
        }
    }

}

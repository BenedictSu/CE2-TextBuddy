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
    
    @Test
    public void testClear() throws IOException{
        try {
            assertEquals("all content deleted from test.txt", TextBuddy.clear());
        } catch (IOException e) {
            throw e;
        }
    }
    
    @Test
    public void testDisplay() throws IOException{
        try {
            TextBuddy.add("add little brown fox");
            assertEquals("1. little brown fox", TextBuddy.display());
            TextBuddy.clear();
            assertEquals("test.txt is empty", TextBuddy.display());
            TextBuddy.add("add little brown fox");
            TextBuddy.add("add jump over the moon");
            assertEquals("1. little brown fox\n2. jump over the moon", TextBuddy.display());          
        } catch (IOException e) {
            throw e;
        }
    }
    
    @Test
    public void testDelete() throws IOException{
        try {
            TextBuddy.clear();
            TextBuddy.add("add little brown fox");
            assertEquals("deleted from test.txt: \"little brown fox\"", TextBuddy.delete("delete 1"));
            assertEquals("test.txt is empty", TextBuddy.display());
            TextBuddy.add("add little brown fox");
            TextBuddy.add("add jump over the moon");
            assertEquals("deleted from test.txt: \"jump over the moon\"", TextBuddy.delete("delete 2"));
            assertEquals("deleted from test.txt: \"little brown fox\"", TextBuddy.delete("delete 1"));
            assertEquals("test.txt is empty", TextBuddy.display());
            TextBuddy.add("add little brown fox");
            TextBuddy.add("add jump over the moon");
            assertEquals("deleted from test.txt: \"little brown fox\"", TextBuddy.delete("delete 1"));
            assertEquals("deleted from test.txt: \"jump over the moon\"", TextBuddy.delete("delete 1"));
            assertEquals("test.txt is empty", TextBuddy.display());
        } catch (IOException e) {
            throw e;
        }
    }

}

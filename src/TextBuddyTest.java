import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest {

    @Test
    public void testAdd() throws IOException {
        assertEquals("added to test.txt: \"little brown fox\"",
                TextBuddy.add("add little brown fox"));
    }

    @Test
    public void testClear() throws IOException {
        assertEquals("all content deleted from test.txt", TextBuddy.clear());
    }

    @Test
    public void testDisplay() throws IOException {
        TextBuddy.clear();
        TextBuddy.add("add little brown fox");
        assertEquals("1. little brown fox", TextBuddy.display());
        TextBuddy.clear();
        assertEquals("test.txt is empty", TextBuddy.display());
        TextBuddy.add("add little brown fox");
        TextBuddy.add("add jump over the moon");
        assertEquals("1. little brown fox\n2. jump over the moon",
                TextBuddy.display());
    }

    @Test
    public void testDelete() throws IOException {
        TextBuddy.clear();
        TextBuddy.add("add little brown fox");
        assertEquals("deleted from test.txt: \"little brown fox\"",
                TextBuddy.delete("delete 1"));
        assertEquals("test.txt is empty", TextBuddy.display());
        TextBuddy.add("add little brown fox");
        TextBuddy.add("add jump over the moon");
        assertEquals("deleted from test.txt: \"jump over the moon\"",
                TextBuddy.delete("delete 2"));
        assertEquals("deleted from test.txt: \"little brown fox\"",
                TextBuddy.delete("delete 1"));
        assertEquals("test.txt is empty", TextBuddy.display());
        TextBuddy.add("add little brown fox");
        TextBuddy.add("add jump over the moon");
        assertEquals("deleted from test.txt: \"little brown fox\"",
                TextBuddy.delete("delete 1"));
        assertEquals("deleted from test.txt: \"jump over the moon\"",
                TextBuddy.delete("delete 1"));
        assertEquals("test.txt is empty", TextBuddy.display());
    }

    @Test
    public void testSort() throws IOException {
        TextBuddy.clear();
        TextBuddy.add("add c");
        TextBuddy.add("add d");
        TextBuddy.add("add B");
        TextBuddy.add("add a");
        assertEquals("test.txt is sorted", TextBuddy.sort());
        assertEquals("1. a\n2. B\n3. c\n4. d", TextBuddy.display());
    }
    
    @Test
    public void testSearch() throws IOException {
        TextBuddy.clear();
        TextBuddy.add("add little brown fox");
        assertEquals("little brown fox", TextBuddy.search("search fox"));
        TextBuddy.clear();
    }
}

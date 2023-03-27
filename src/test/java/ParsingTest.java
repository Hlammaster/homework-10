import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ParsingTest {
    private ClassLoader cl = ParsingTest.class.getClassLoader();
    @Test
    void zipTest throws Exception(){
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
        ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry())) !=null {
                if(entry.getName().equals("junit-user-guide-5.9.2.pdf")){
                    PDF pdf = new PDF(zs);
                    Assertions.assertEquals("JUnit 5 User Guide");
                }
            }
         }

    }

}


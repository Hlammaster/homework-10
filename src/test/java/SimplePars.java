import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimplePars {





    private ClassLoader cl = SimplePars.class.getClassLoader();
    @Test
    void pdfTest() throws Exception {
        InputStream stream = cl.getResourceAsStream("junit-user-guide-5.9.2.pdf");
        PDF pdf = new PDF(stream);
        Assertions.assertEquals("JUnit 5 User Guide",pdf.title);

    }
    @Test
    void csvParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test.csv");
             InputStreamReader isr = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(isr);
            List<String[]> content = csvReader.readAll();
            System.out.println();
            Assertions.assertArrayEquals(new String[] {"Morrowind", "RPG"}, content.get(1));
        }
    }

    @Test
    void xlsTest() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("123.xlsm");
        XLS xls = new XLS(stream);
        System.out.println();
    }

}



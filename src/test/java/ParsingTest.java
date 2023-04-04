import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.hamcrest.MatcherAssert.assertThat;

public class ParsingTest {
    private ClassLoader cl = ParsingTest.class.getClassLoader();


        @Test
        void readCsvFromZip() throws Exception {
            try (InputStream files = cl.getResourceAsStream("ziptest.zip");
                 ZipInputStream zs = new ZipInputStream(files)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().equals("test.csv")) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                        List<String[]> content = csvReader.readAll();
                        Assertions.assertArrayEquals(new String[] {"Morrowind", "RPG"}, content.get(1));
                    }
                }
            }
        }

        @Test
        void readTitleInPdfFromZip() throws Exception{
            try (InputStream files = cl.getResourceAsStream("ziptest.zip");
                 ZipInputStream zs = new ZipInputStream(files)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().equals("sample.pdf")) {
                        PDF pdf = new PDF(zs);
                        Assertions.assertEquals("JUnit 5 User Guide",pdf.title);
                    }
                }
            }

        }
}
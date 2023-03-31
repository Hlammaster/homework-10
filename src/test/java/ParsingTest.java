import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
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

public class ParsingTest {
    private ClassLoader cl = ParsingTest.class.getClassLoader();

    @Test
    void zipCsvParsingTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/ziptest.zip"));
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("test.csv"))
                try (InputStream inputStreamCsv = zipFile.getInputStream(entry)){
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                    List<String[]> string = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"Morrowind", "RPG"}, string.get(1));


                }
            }
        }

    }
    @Test
    void zipPdfParsingTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/ziptest.zip"));
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("junit-user-guide-5.9.2.pdf"))
                    try (InputStream inputStreamPdf = zipFile.getInputStream(entry)){
                        PDF newPdf = new PDF(inputStreamPdf);
                        Assertions.assertEquals("JUnit 5 User Guide",newPdf.title);

                    }
            }
        }


    }


}
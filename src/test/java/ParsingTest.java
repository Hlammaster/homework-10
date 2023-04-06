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

public class ParsingTest {
    private ClassLoader cl = ParsingTest.class.getClassLoader();


    @Test
    void readCsvFromZip() throws Exception {
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            boolean found = false;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("test.csv")) {
                    found = true;
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                    List<String[]> content = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"Morrowind", "RPG"}, content.get(1));
                }
            }
            if (!found) {
                Assertions.fail("File wasn't found");
            }
        }
    }

    @Test
    void readPdfFromZip() throws Exception {
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            boolean found = false;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("junit-user-guide-5.9.2.pdf")) {
                    found = true;
                    PDF pdf = new PDF(zs);
                    Assertions.assertEquals("JUnit 5 User Guide", pdf.title);
                }
            }
            if (!found) {
                Assertions.fail("File wasn't found");
            }
        }

    }

    @Test
    void readXlsFromZip() throws Exception{
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            boolean found = false;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("666.xls")) {
                    found = true;
                    XLS xls = new XLS(zs);
                    Assertions.assertTrue(xls.excel.getSheetAt(1).getRow(0).getCell(2)
                            .getStringCellValue().startsWith("Возраст"));
                }
            }
            if (!found) {
                Assertions.fail("File wasn't found");
            }

        }
    }
}
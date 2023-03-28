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
    void zipCsvParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("test.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                    List<String[]> string = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"Morrowind", "RPG"}, string.get(1));


                }
            }
        }

    }


    @Test
    void zipPdfParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("junit-user-guide-5.9.2.pdf")) {
                    PDF pdf = new PDF(zs);
                    Assertions.assertEquals("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein",
                            pdf.author);
                }
            }
        }


    }
    @Test
    void zipXlsParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("ziptest.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("Grafik_otpuskov_2016_full_holidays (1).xls\"")) {
                    XLS xls = new XLS(zs);
                    Assertions.assertTrue(xls.excel.getSheetAt(0).getRow(8).getCell(1).getStringCellValue().startsWith("Сотрудник"));

                }
            }
        }


    }

}


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonParsingTest {
    private ClassLoader cl = ParsingTest.class.getClassLoader();

    @Test
    void jsonCleverTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("morrowind.json");
             InputStreamReader isr = new InputStreamReader(is)) {

            JsonData game = mapper.readValue(isr, JsonData.class);

            Assertions.assertEquals("Morrowind", game.name);
            Assertions.assertEquals(2003, game.releasedata);
            Assertions.assertEquals(List.of("RPG","Action"), game.genre);
            Assertions.assertTrue(game.singleplayer);
            Assertions.assertEquals(999,game.price);
        }
    }
}

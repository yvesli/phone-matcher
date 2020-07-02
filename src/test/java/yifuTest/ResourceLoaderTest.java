package yifuTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yifu.ResourceLoader;

public class ResourceLoaderTest {
    @Test
    void testConfigLoader() {
        String size = ResourceLoader.getInstance().getConfig("file.size.threshold");
        Assertions.assertEquals("2GB", size);
    }
}

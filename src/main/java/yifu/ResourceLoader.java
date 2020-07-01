package yifu;

import java.io.File;

public class ResourceLoader {

    public File getFile(String fileName) {
        return new File(getClass().getResource(fileName).getFile());
    }

}

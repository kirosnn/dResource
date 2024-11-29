package fr.kirosnn.kResource.managers;

import java.io.File;
import java.util.List;

public class AutoPackData {
    private final File file;
    private final List<String> skipFiles;

    public AutoPackData(File file, List<String> skipFiles) {
        this.file = file;
        this.skipFiles = skipFiles;
    }

    public File getFile() {
        return file;
    }

    public List<String> getSkipFiles() {
        return skipFiles;
    }
}

package com.google.refine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *  Due to a constraint in the Butterfly library used by OpenRefine (see Butterfly line 191, context.getRealPath("/") does not support resources)
 *  we need to load the resources from the code to the filesystem. This class does that by copying the contents from resources to the disk
 *  and unzipping it.
 */
public class WebappExtractor {

    final static Logger logger = LoggerFactory.getLogger("WebappExtractor");

    private String webappDir;
    private String zipFileName;

    public WebappExtractor(String zipFileName, String webappDir) {
        this.webappDir = webappDir;
        this.zipFileName = zipFileName;
    }

    public void init() {
        try {
            Files.copy(getClass().getResourceAsStream("/" + zipFileName), Paths.get(zipFileName), StandardCopyOption.REPLACE_EXISTING);
            ZipHelper.extract(zipFileName, webappDir);
        } catch (Exception e) {
            logger.error("Could not extract webapp directory from resources into current folder. This is required by OpenRefine Buttefly servlet library.", e);
        }
    }
}

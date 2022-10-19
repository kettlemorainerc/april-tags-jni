package com.squedgy.frc.team2077.april.tags;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AprilTag {
    private AprilTag() {throw new RuntimeException("Don't instantiate me");}

    private static boolean init;
    public static void initialize() throws IOException {
        if(init) return;
        init = true;

        clone("april_tags_2077.dll", "april_tags_2077.dll");
        clone("apriltagd.lib", "apriltagd.lib");

        System.loadLibrary("april_tags_2077");
    }
    
    private static void clone(String file, String to) throws IOException {
        try (
                InputStream library = AprilTag.class.getResourceAsStream("/" + file);
                FileOutputStream output = new FileOutputStream("." + File.separator + to)
        ) {
            int val;
            while((val = library.read()) != -1) {
                output.write(val);
            }
        }
    }
}

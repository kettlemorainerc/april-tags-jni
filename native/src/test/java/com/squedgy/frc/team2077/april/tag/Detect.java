package com.squedgy.frc.team2077.april.tag;


import com.squedgy.frc.team2077.april.tags.AprilTag;
import com.squedgy.frc.team2077.april.tags.ByteImage;
import com.squedgy.frc.team2077.april.tags.TagFamily;
import com.squedgy.frc.team2077.april.tags.detection.Detection;
import com.squedgy.frc.team2077.april.tags.detection.DetectionResult;
import com.squedgy.frc.team2077.april.tags.detection.Detector;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.osgi.OpenCVNativeLoader;

import java.io.File;
import java.io.IOException;

public class Detect {
    private static final String TAG_DIR = "C:\\Users\\dafma\\jungle\\robos\\apriltag-generation\\tags";

    @Test
    void try_detect() throws IOException {
        File tagsDir = new File(TAG_DIR);

        System.out.printf(
                "[java.home=%s][java.version=%s]%n",
            System.getProperty("java.home"),
                System.getProperty("java.version")
        );


        nu.pattern.OpenCV.loadShared();
        AprilTag.initialize();
        Detector detector = new Detector(TagFamily.TAG_36H11);

        File[] files = tagsDir.listFiles((f, name) -> name.endsWith(".png"));
        for(File imageFile : files) {
            Mat mat = org.opencv.imgcodecs.Imgcodecs.imread(imageFile.getAbsolutePath());

            ByteImage image = new ByteImage(mat.rows(), mat.cols(), mat.dataAddr());
            DetectionResult search = detector.search(image);

            System.out.printf("Detected %s images within %s%n", search.length, imageFile.getName());
            for(Detection detection : search) {
                System.out.printf("%s%n", detection);
            }
        }
    }
}

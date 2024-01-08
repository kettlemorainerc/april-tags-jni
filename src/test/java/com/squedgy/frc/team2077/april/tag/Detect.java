package com.squedgy.frc.team2077.april.tag;


import com.squedgy.frc.team2077.april.tags.AprilTag;
import com.squedgy.frc.team2077.april.tags.ByteImage;
import com.squedgy.frc.team2077.april.tags.TagFamily;
import com.squedgy.frc.team2077.april.tags.detection.Detection;
import com.squedgy.frc.team2077.april.tags.detection.DetectionResult;
import com.squedgy.frc.team2077.april.tags.detection.Detector;
import com.squedgy.frc.team2077.april.tags.detection.Point;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.osgi.OpenCVNativeLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Detect {
//    private static final String TAG_DIR = "C:\\Users\\dafma\\jungle\\robos\\apriltag-generation\\tags";
    private static final String TAG_DIR = "C:\\Users\\dafma\\jungle\\robos\\apriltag-generation\\tags";

    @Test
    void try_detect() throws IOException {
        File tagsDir = new File(TAG_DIR);

        nu.pattern.OpenCV.loadShared();
        AprilTag.initialize();
        Detector detector = new Detector(TagFamily.TAG_36H11);
        detector.setMinWhiteBlackDiff(1);
        detector.setMinClusterPixels(1);
        detector.setQuadDecimate(1);
//            detector.setConsideredCornerCandidates(5);
//            detector.setDebug(true);

        File[] files = tagsDir.listFiles((f, name) -> name.endsWith(".png"));
        System.out.printf("%s is a dir %s%n", tagsDir, tagsDir.isDirectory());
        System.out.printf("Detecting files within %s%n", Arrays.toString(files));
        for(File imageFile : files) {
            Mat mat = org.opencv.imgcodecs.Imgcodecs.imread(imageFile.getAbsolutePath());
            Mat gray = new Mat();
            Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);

            ByteImage image = new ByteImage(gray.rows(), gray.cols(), gray.dataAddr());
            DetectionResult search = detector.search(image);

            System.out.printf("Detected %s tags within %s%n", search.length, imageFile.getName());
            for(Detection detection : search) {
                System.out.printf("%s at %s%n", detection, detection.getCenter());
                Point[] corners = detection.getCorners();
                System.out.printf("corners at %s%n", Arrays.toString(corners));
            }
        }

    }
}

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
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.osgi.OpenCVNativeLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Detect {
//    private static final String TAG_DIR = "C:\\Users\\dafma\\jungle\\robos\\apriltag-generation\\tags";
    private static final String TAG_DIR = "C:\\Users\\dafma\\jungle\\robos\\april-tags\\native\\fake-tag";

    @Test
    void try_detect() throws IOException {
        File tagsDir = new File(TAG_DIR);

        nu.pattern.OpenCV.loadShared();
        AprilTag.initialize();
        try(Detector detector = new Detector(TagFamily.TAG_36H11)) {
            detector.setMinWhiteBlackDiff(1);
            detector.setMinClusterPixels(5);
            detector.setQuadDecimate(1);
            detector.setConsideredCornerCandidates(200);
            detector.setDebug(true);

            File[] files = tagsDir.listFiles((f, name) -> name.endsWith(".png"));
            System.out.printf("Detecting files within %s%n", Arrays.toString(files));
            for(File imageFile : files) {
                Mat mat = org.opencv.imgcodecs.Imgcodecs.imread(imageFile.getAbsolutePath());
                Mat gray = new Mat();
                Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);

                ByteImage image = new ByteImage(gray.rows(), gray.cols(), gray.dataAddr());
                System.out.printf("60, 60: %s%n", Arrays.toString(gray.get(60, 60)));
                System.out.printf("0, 0: %s%n", Arrays.toString(gray.get(0, 0)));
                System.out.printf("Trying %s x %s image %s%n", mat.rows(), mat.cols(), imageFile.getName());
                DetectionResult search = detector.search(image);

                System.out.printf("Detected %s tags within %s%n", search.length, imageFile.getName());
                for(Detection detection : search) {
                    System.out.printf("%s%n", detection);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

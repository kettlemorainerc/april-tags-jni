package com.squedgy.frc.team2077.april.tags.detection;

import com.squedgy.frc.team2077.april.tags.ByteImage;
import com.squedgy.frc.team2077.april.tags.TagFamily;

import java.lang.ref.Cleaner;

public class Detector implements AutoCloseable {
    /*JNI
#include "jni.h"
#include "april-tags/apriltag.h"
#include <iostream>
#define AS_PTR(thing) ((void*) (int64_t) thing)
#define THIS(thing) ((apriltag_detector_t *) AS_PTR(thing))
#define SET(thing, field, value) thing -> field = value
#define SETP(thing, field, value) SET(thing, qtp . field, value)

*/

    private static final Cleaner cleaner = Cleaner.create();

    private final Cleaner.Cleanable cleanable;
    private final long ptr;
    private final long[] families;
    private boolean closed;

    public Detector(TagFamily family) {
        families = new long[1];
        ptr = newDetector(this, family);
        cleanable = cleaner.register(this, new CleanupAction(ptr, families));
    }

    public void setThreads(int count) {set_threads(ptr, count);}
    private static native void set_threads(long ptr, int count);/* SET(THIS(ptr), nthreads, count);*/

    public void setQuadDecimate(float decimate) {set_quad_decimate(ptr, decimate);}
    private static native void set_quad_decimate(long ptr, float decimate); /* SET(THIS(ptr), quad_decimate, decimate); */

    public void setQuadSigma(float sigma) {set_quad_sigma(ptr, sigma);}
    private static native void set_quad_sigma(long ptr, float sigma); /* SET(THIS(ptr), quad_sigma, sigma);*/

    public void setRefineEdges(boolean refine) {set_refine_edges(ptr, refine);}
    private static native void set_refine_edges(long ptr, boolean refine); /* SET(THIS(ptr), refine_edges, refine);*/

    public void setDecodeSharpening(double sharpening) {set_decode_sharpening(ptr, sharpening);}
    private static native void set_decode_sharpening(long ptr, double sharp); /* SET(THIS(ptr), decode_sharpening, sharp);*/

    public void setDebug(boolean debug) {set_debug(ptr, debug);}
    private static native void set_debug(long ptr, boolean debug); /* SET(THIS(ptr), debug, debug);*/

    public void setMinClusterPixels(int min) {set_min_cluster_pixels(ptr, min);}
    private static native void set_min_cluster_pixels(long ptr, int min); /* SETP(THIS(ptr), min_cluster_pixels, min);*/

    public void setConsideredCornerCandidates(int amount) {set_max_nmaxima(ptr, amount);}
    private static native void set_max_nmaxima(long ptr, int amount); /* SETP(THIS(ptr), max_nmaxima, amount);*/

    public void setCriticalRadians(float rads) {set_critical_rad(ptr, rads);}
    private static native void set_critical_rad(long ptr, float rads); /* SETP(THIS(ptr), critical_rad, rads);*/

    public void setMaxLineFitMeanSquareError(float mse) {set_max_line_fit_mse(ptr, mse);}
    private static native void set_max_line_fit_mse(long ptr, float mse); /* SETP(THIS(ptr), max_line_fit_mse, mse);*/

    public void setMinWhiteBlackDiff(float diff) {set_min_white_black_diff(ptr, diff);}
    private static native void set_min_white_black_diff(long ptr, float diff); /* SETP(THIS(ptr), min_white_black_diff, diff);*/

    public void setDeglitch(int deglitch) {set_deglitch(ptr, deglitch);}
    private static native void set_deglitch(long ptr, int deglitch); /* SETP(THIS(ptr), deglitch, deglitch);*/

    public DetectionResult search(ByteImage image) {
        if(closed) throw new IllegalStateException("Detector has already been closed!");

        long resultPtr = search(ptr, image.getPointer());

        return new DetectionResult(resultPtr);
    }

    private static native long search(long detectPtr, long imagePtr); /*
    apriltag_detector_t *detector = (apriltag_detector_t *) detectPtr;
    image_u8_t *image = (image_u8_t *) imagePtr;

    zarray_t *arrPtr = apriltag_detector_detect(detector, image);

    return (jlong) (int64_t) arrPtr;*/

    private static long newDetector(Detector detector, TagFamily... families) {
        long[] ptrs = new long[families.length];
        for(int i = 0 ; i < families.length; i++) {
            detector.families[i] = ptrs[i] = families[i].getReference();
        }

        return makeDetector(ptrs, ptrs.length);
    }

    private static native long makeDetector(long[] ptrs, int count); /*
    int i;
    std::cout << "Making detector with " << count << " families" << std::endl;
    apriltag_detector_t *detector = apriltag_detector_create();
    for(i = 0; i < count; i++) {
        std::cout << "Adding family #" << i << std::endl;
        apriltag_detector_add_family(detector, (apriltag_family_t*) ptrs[i]);
    }

    return (jlong) detector;*/

    @Override
    public void close() throws Exception {
        closed = true;
    }

    private static class CleanupAction implements Runnable {
        private final long detectorPtr;
        private final long[] families;

        CleanupAction(long ptr, long[] families) {
            this.detectorPtr = ptr;
            this.families = families;
        }

        @Override public void run() {
            clean_detector(detectorPtr);
            for(long ptr : families) {
                TagFamily family = TagFamily.fromNativePointer(ptr);
                family.cleanup.accept(ptr);
            }
        }

        private static native void clean_detector(long ptr); /* apriltag_detector_destroy((apriltag_detector_t *) ptr); */
    }
}

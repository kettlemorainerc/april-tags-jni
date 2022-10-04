package com.squedgy.frc.team2077.april.tags.detection;

import com.squedgy.frc.team2077.april.tags.ByteImage;
import com.squedgy.frc.team2077.april.tags.TagFamily;

public class Detector {
    /*JNI
#include "jni.h"
#include "april-tags/apriltag.h"

*/

    private final long ptr;

    public Detector(TagFamily family) {
        ptr = newDetector(family);
    }

    public DetectionResult search(ByteImage image) {
        long resultPtr = search(ptr, image.getPointer());

        return new DetectionResult(resultPtr);
    }

    private static native long search(long detectPtr, long imagePtr); /*
    return (jlong) apriltag_detector_detect((apriltag_detector_t *) detectPtr, (image_u8_t *) imagePtr);*/

    private static long newDetector(TagFamily... families) {
        long[] ptrs = new long[families.length];
        for(int i = 0 ; i < families.length; i++) {
            ptrs[i] = families[i].getReference();
        }

        return makeDetector(ptrs, ptrs.length);
    }

    private static native long makeDetector(long[] ptrs, int count); /*
    int i;
    apriltag_detector_t *detector = apriltag_detector_create();
    for(i = 0; i < count; i++) {
        apriltag_detector_add_family(detector, (apriltag_family_t*) ptrs[i]);
    }
    return (jlong) detector;
*/
}

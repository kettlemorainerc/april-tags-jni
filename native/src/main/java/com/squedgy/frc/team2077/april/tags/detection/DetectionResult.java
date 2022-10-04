package com.squedgy.frc.team2077.april.tags.detection;

public class DetectionResult {
    /*JNI
#include "jni.h"
#include "april-tags/apriltag.h"

*/

    private final long ptr;
    public final int length;
    DetectionResult(long ptr) {
        this.ptr = ptr;
        length = size(ptr);
    }

    public Detection get(int i) {
        if(i >= length || i < 0) {
            throw new IndexOutOfBoundsException(String.format(
                    "requested index (#%s) is not a valid index for array of length %s",
                    i, length
            ));
        }

        return new Detection(get(ptr, i));
    }

    private static native long get(long ptr, int i); /*
    apriltag_detection_t *ref;
    zarray_get((zarray_t *) ptr, i, &ref);

    return (jlong) ref;
*/

    private static native int size(long of); /*
    return (jint) zarray_size((zarray_t *) of);
*/
}

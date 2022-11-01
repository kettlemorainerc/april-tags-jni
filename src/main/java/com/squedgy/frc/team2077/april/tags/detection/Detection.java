package com.squedgy.frc.team2077.april.tags.detection;

import com.squedgy.frc.team2077.april.tags.TagFamily;

import java.nio.charset.StandardCharsets;

public class Detection {
    /*JNI
#include "jni.h"
#include "april-tags/apriltag.h"
#include <iostream>
#define THIS(thing) ((apriltag_detection *) thing)
#define FAM(thing) ((apriltag_family_t *) thing)

*/

    private final long ptr;
    Detection(long ptr) {;
        this.ptr = ptr;
    }

    public TagFamily getFamily() {
        return TagFamily.fromNativePointer(familyPtr(ptr));
    }

    private static native long familyPtr(long ptr); /*
    apriltag_detection_t *detection = THIS(ptr);
    return (jlong) detection->family;
*/

    public int getTagId() {return id(ptr);}
    private static native int id(long ptr); /* return THIS(ptr)->id; */

    public int getErrorBits() {return hamming(ptr);}
    private static native int hamming(long ptr); /* return THIS(ptr)->hamming; */

    public double getDecisionMargin() {return decisionMargin(ptr);}
    private static native double decisionMargin(long ptr); /* return THIS(ptr)->decision_margin; */

    public Point getCenter() {
        double[] center = new double[2];
        populate_center(ptr, center);

        return new Point(center[0], center[1]);
    }
    private static native void populate_center(long ptr, double[] ctr); /*
    const jdouble vals[2] = {THIS(ptr)->c[0], THIS(ptr)->c[1]};
    const jdouble* values = (jdouble *)vals;

    env->SetDoubleArrayRegion(obj_ctr, 0, 2, values); */

    public Point[] getCorners() {
        Point[] ret = new Point[4];

        for(int i = 0; i < ret.length; i++) {
            double[] coords = new double[2];
            populate_corner(ptr, coords, i);

            ret[i] = new Point(coords[0], coords[1]);
        }

        return ret;
    }
    private static native void populate_corner(long ptr, double[] point, int pt); /*
    const jdouble* values = (jdouble *)(THIS(ptr)->p[pt]);
    env->SetDoubleArrayRegion(obj_point, 0, 2, values); */

    @Override
    public String toString() {
        return String.format(
                "Detection[id=%s][family=%s][margin=%s][hamming=%s]",
                getTagId(),
                getFamily().name,
                getDecisionMargin(),
                getErrorBits()
        );
    }
}

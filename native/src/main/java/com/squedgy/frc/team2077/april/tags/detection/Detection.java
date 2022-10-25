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

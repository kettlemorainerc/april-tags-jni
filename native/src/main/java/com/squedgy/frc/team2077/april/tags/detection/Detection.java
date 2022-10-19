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
        String name = new String(familyOf(ptr), StandardCharsets.UTF_8);

        return TagFamily.ofName(name);
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

    private static native byte[] familyOf(long ptr); /*
    apriltag_detection_t *detect = THIS(ptr);
    char *name = detect->family->name;

    // "Strings" are typically null ('\0') delimited. Detect length beforehand for populating the returned bytes
    int name_size;
    for(name_size = 0; (name[name_size]) != '\0'; name_size++);

    jbyteArray arr = env->NewByteArray(name_size);

    env->SetByteArrayRegion(arr, 0, name_size, (const jbyte *) name);

    return arr;*/

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

package com.squedgy.frc.team2077.april.tags;

import java.nio.charset.StandardCharsets;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;

public enum TagFamily {
    TAG_16h5("tag16h5", TagFamily::tag16h5, TagFamily::tag16h5_destroy),
    TAG_16H9("tag16h9", TagFamily::tag16h5, TagFamily::tag16h5_destroy),
    TAG_25H9("tag25h9", TagFamily::tag25h9, TagFamily::tag25h9_destroy),
    TAG_36H10("tag36h10", TagFamily::tag36h10, TagFamily::tag36h10_destroy),
    TAG_36H11("tag36h11", TagFamily::tag36h11, TagFamily::tag36h11_destroy),
    TAG_CIRCLE_21H7("tagCircle21h7", TagFamily::tagCircle21h7, TagFamily::tagCircle21h7_destroy),
    TAG_CIRCLE_49H12("tagCircle49h12", TagFamily::tagCircle49h12, TagFamily::tagCircle49h12_destroy),
    TAG_CUSTOM_48H12("tagCustom48h12", TagFamily::tagCustom48h12, TagFamily::tagCustom48h12_destroy),
    TAG_STANDARD_41H12("tagStandard41h12", TagFamily::tagStandard41h12, TagFamily::tagStandard41h12_destroy),
    TAG_STANDARD_52H13("tagStandard52h13", TagFamily::tagStandard52h13, TagFamily::tagStandard52h13_destroy),
    ;

    /*JNI
#include "jni.h"
#include "april-tags/apriltag.h"
#include "april-tags/tag16h5.h"
#include "april-tags/tag25h9.h"
#include "april-tags/tag36h10.h"
#include "april-tags/tag36h11.h"
#include "april-tags/tagCircle21h7.h"
#include "april-tags/tagCircle49h12.h"
#include "april-tags/tagCustom48h12.h"
#include "april-tags/tagStandard41h12.h"
#include "april-tags/tagStandard52h13.h"

*/

    private final LongSupplier pointerProvider;
    public final LongConsumer cleanup;
    public final String name;

    TagFamily(String name, LongSupplier creator, LongConsumer cleanup) {
        this.name = name;
        this.pointerProvider = creator;
        this.cleanup = cleanup;
    }

    public long getReference() {
        return pointerProvider.getAsLong();
    }

    public static TagFamily ofName(String name) {
        for(TagFamily family : TagFamily.values()) if(family.name.equals(name)) return family;

        throw new IllegalArgumentException(String.format(
                "Unsupported/Unknown family name: %s",
                name
        ));
    }
    
    public static TagFamily fromNativePointer(long pointer) {
        return ofName(new String(nameBytesOfPtr(pointer), StandardCharsets.UTF_8));
    }
    
    public static native byte[] nameBytesOfPtr(long ptr); /*
    apriltag_family_t *family = (apriltag_family_t *) ptr;
    char *name = family->name;

    // "Strings" are typically null ('\0') delimited. Detect length beforehand for populating the returned bytes
    int name_size;
    for(name_size = 0; (name[name_size]) != '\0'; name_size++);

    jbyteArray arr = env->NewByteArray(name_size);

    env->SetByteArrayRegion(arr, 0, name_size, (const jbyte *) name);

    return arr;*/

    private static native long tag16h5(); /*return (jlong) tag16h5_create();*/
    private static native void tag16h5_destroy(long pointer); /* tag16h5_destroy((apriltag_family_t *) pointer);*/
    private static native long tag25h9(); /*return (jlong) tag25h9_create();*/
    private static native void tag25h9_destroy(long pointer); /* tag25h9_destroy((apriltag_family_t *) pointer);*/
    private static native long tag36h10(); /*return (jlong) tag36h10_create();*/
    private static native void tag36h10_destroy(long pointer); /* tag36h10_destroy((apriltag_family_t *) pointer);*/
    private static native long tag36h11(); /*return (jlong) tag36h11_create();*/
    private static native void tag36h11_destroy(long pointer); /* tag36h11_destroy((apriltag_family_t *) pointer);*/
    private static native long tagCircle21h7(); /*return (jlong) tagCircle21h7_create();*/
    private static native void tagCircle21h7_destroy(long pointer); /* tagCircle21h7_destroy((apriltag_family_t *) pointer);*/
    private static native long tagCircle49h12(); /*return (jlong) tagCircle49h12_create();*/
    private static native void tagCircle49h12_destroy(long pointer); /* tagCircle49h12_destroy((apriltag_family_t *) pointer);*/
    private static native long tagCustom48h12(); /*return (jlong) tagCustom48h12_create();*/
    private static native void tagCustom48h12_destroy(long pointer); /* tagCustom48h12_destroy((apriltag_family_t *) pointer);*/
    private static native long tagStandard41h12(); /*return (jlong) tagStandard41h12_create();*/
    private static native void tagStandard41h12_destroy(long pointer); /* tagStandard41h12_destroy((apriltag_family_t *) pointer);*/
    private static native long tagStandard52h13(); /*return (jlong) tagStandard52h13_create();*/
    private static native void tagStandard52h13_destroy(long pointer); /* tagStandard52h13_destroy((apriltag_family_t *) pointer);*/
}

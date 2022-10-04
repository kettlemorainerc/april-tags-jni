package com.squedgy.frc.team2077.april.tags;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

public enum TagFamily {
    TAG_16h5("tag16h5", TagFamily::tag16h5),
    TAG_16H9("tag16h9", TagFamily::tag16h5),
    TAG_25H9("tag25h9", TagFamily::tag25h9),
    TAG_36H10("tag36h10", TagFamily::tag36h10),
    TAG_36H11("tag36h11", TagFamily::tag36h11),
    TAG_CIRCLE_21H7("tagCircle21h7", TagFamily::tagCircle21h7),
    TAG_CIRCLE_49H12("tagCircle49h12", TagFamily::tagCircle49h12),
    TAG_CUSTOM_48H12("tagCustom48h12", TagFamily::tagCustom48h12),
    TAG_STANDARD_41H12("tagStandard41h12", TagFamily::tagStandard41h12),
    TAG_STANDARD_52H13("tagStandard52h13", TagFamily::tagStandard52h13),
    ;

    private final LongSupplier pointerProvider;
    public final String name;

    TagFamily(String name, LongSupplier creator) {
        this.name = name;
        this.pointerProvider = creator;
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

    /*JNI
#include "jni.h"
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

    private static native long tag16h5(); /*return (jlong) tag16h5_create();*/
    private static native long tag25h9(); /*return (jlong) tag25h9_create();*/
    private static native long tag36h10(); /*return (jlong) tag36h10_create();*/
    private static native long tag36h11(); /*return (jlong) tag36h11_create();*/
    private static native long tagCircle21h7(); /*return (jlong) tagCircle21h7_create();*/
    private static native long tagCircle49h12(); /*return (jlong) tagCircle49h12_create();*/
    private static native long tagCustom48h12(); /*return (jlong) tagCustom48h12_create();*/
    private static native long tagStandard41h12(); /*return (jlong) tagStandard41h12_create();*/
    private static native long tagStandard52h13(); /*return (jlong) tagStandard52h13_create();*/
}

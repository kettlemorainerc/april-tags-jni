package com.squedgy.frc.team2077.april.tags;

public class ByteImage {
    /*JNI
#include "april-tags/common/image_types.h"
#include "jni.h"
#include <iostream>
#define AS_PTR(thing) ((void*) (int64_t) thing)

*/

    private final long ptr;
    public ByteImage(int rows, int cols, long dataPointer) {
        ptr = makeNewImage(rows, cols, dataPointer);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        clear(ptr);
    }

    public long getPointer() {return ptr;}

    private static native void clear(float ptr); /*
    delete ((image_u8_t*) AS_PTR(ptr)); */

    private static native long makeNewImage(int rows, int cols, long data); /*
    const int32_t height = rows;
    const int32_t width = cols;
    uint8_t *buf = (uint8_t *) AS_PTR(data);
    image_u8_t *image = new image_u8_t{width, height, width, buf};

    std::cout << "Accessing the entirety of the buffer" << std::endl;

    int col, row;
    for(row = 0; row < height; row++) {
        for(col = 0; col < width; col++) {
            std::cout << " " << buf[(row * height) + col];
        }
        std::cout << std::endl;
    }

    return (jlong) image;*/
}

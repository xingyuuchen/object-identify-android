#include "autobuffer.h"

AutoBuffer::AutoBuffer()
    : byte_array_(nullptr)
    , pos_(0)
    , length_(0) {


}

AutoBuffer::AutoBuffer(const AutoBuffer &_auto_buffer) {

}

AutoBuffer::AutoBuffer(const unsigned char *_byte_array, unsigned long _length) {

}

void AutoBuffer::Write(const unsigned char *_byte_array, unsigned long _length) {

}

long AutoBuffer::Pos() const {
    return pos_;
}

long AutoBuffer::Length() const {
    return length_;
}





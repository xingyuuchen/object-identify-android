#include <stdlib.h>
#include "autobuffer.h"

AutoBuffer::AutoBuffer()
    : byte_array_(nullptr)
    , pos_(0)
    , length_(0) {


}

AutoBuffer::AutoBuffer(const AutoBuffer &_auto_buffer) {

}

AutoBuffer::AutoBuffer(const unsigned char *_byte_array, size_t _len) {

}

void AutoBuffer::Write(const unsigned char *_byte_array, size_t _len) {
    if (_len <= 0 || _byte_array == NULL) {
        return;
    }
    memcpy(Ptr(), _byte_array, _len);
    length_ += _len;
}

off_t AutoBuffer::Pos() const {
    return pos_;
}

size_t AutoBuffer::Length() const {
    return length_;
}

unsigned char *AutoBuffer::Ptr() const {
    return byte_array_;
}





#ifndef OI_AUTOBUFFER_H
#define OI_AUTOBUFFER_H

#include <string.h>


class AutoBuffer {
  public:
    AutoBuffer();
    AutoBuffer(const AutoBuffer &_auto_buffer);
    AutoBuffer(const unsigned char *_byte_array, size_t _len);

    void Write(const unsigned char *_byte_array, size_t _len);

    off_t Pos() const;

    size_t Length() const;

    unsigned char *Ptr() const;


  private:
    unsigned char *byte_array_;
    off_t pos_;
    size_t length_;

};


#endif //OI_AUTOBUFFER_H

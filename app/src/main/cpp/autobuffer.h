#ifndef OI_AUTOBUFFER_H
#define OI_AUTOBUFFER_H


class AutoBuffer {
  public:
    AutoBuffer();
    AutoBuffer(const AutoBuffer &_auto_buffer);
    AutoBuffer(const unsigned char *_byte_array, unsigned long _length);

    void Write(const unsigned char *_byte_array, unsigned long _length);

    long Pos() const;

    long Length() const;


  private:
    unsigned char *byte_array_;
    long pos_;
    long length_;

};


#endif //OI_AUTOBUFFER_H

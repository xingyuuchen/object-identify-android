#ifndef OI_AUTOBUFFER_H
#define OI_AUTOBUFFER_H


class AutoBuffer {
  public:
    AutoBuffer(size_t _malloc_unit_size = 128);
    AutoBuffer(const AutoBuffer &_auto_buffer);

    ~AutoBuffer();

    void Write(const unsigned char *_byte_array, size_t _len);

    size_t Pos() const;

    size_t Length() const;

    size_t AvailableSize() const;

    void SetLength(size_t _len);

    void AddLength(size_t _len);

    unsigned char *Ptr(const size_t _offset = 0) const;

    size_t GetCapacity() const;

    void AddCapacity(size_t _size);

    void Reset();


  private:
    unsigned char * byte_array_;
    size_t           pos_;
    size_t          length_;
    size_t          capacity_;
    const size_t    malloc_unit_size_;

};


#endif //OI_AUTOBUFFER_H

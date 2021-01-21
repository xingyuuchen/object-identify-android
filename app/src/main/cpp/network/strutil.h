#ifndef OI_STRUTIL_H
#define OI_STRUTIL_H

#include <stdint.h>
#include <string.h>


namespace oi {

inline char *strnstr(const char *_haystack,
                     const char *_needle, size_t _len) {
    if (_haystack == NULL || _needle == NULL) { return NULL; }

    int len1, len2;
    len2 = (int) strlen(_needle);

    if (!len2) { return (char *) _haystack; }

    len1 = (int) strnlen(_haystack, _len);
    _len = _len > len1 ? len1 : _len;

    while (_len >= len2) {
        _len--;
        if (!memcmp(_haystack, _needle, (size_t) len2)) {
            return (char *) _haystack;
        }
        _haystack++;
    }

    return NULL;
}

}

#endif //OI_STRUTIL_H

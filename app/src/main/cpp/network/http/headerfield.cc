#include "headerfield.h"
#include "../log.h"
#include <string.h>


namespace http {

const char *const HeaderField::KHost = "Host";
const char *const HeaderField::KContentLength = "Content-Length";
const char *const HeaderField::KTransferEncoding = "Content-Length";
const char *const HeaderField::KConnection = "Connection";


const char *const HeaderField::KOctetStream = "application/octet-stream";
const char *const HeaderField::KConnectionClose = "close";


void HeaderField::InsertOrUpdateHeader(const std::string &_key,
                                       const std::string &_value) {
    header_fields_[_key] = _value;
}


size_t HeaderField::GetHeaderSize() {
    size_t ret = 0;
    for (auto entry = header_fields_.begin(); entry != header_fields_.end(); entry++) {
        ret += entry->first.size();
        ret += entry->second.size();
        ret += 3;
    }
    return ret;
}

uint64_t HeaderField::GetContentLength() const {
    for (auto iter = header_fields_.begin(); iter != header_fields_.end(); iter++) {
        if (0 == strcmp(iter->first.c_str(), KContentLength)) {
            return strtoul(iter->second.c_str(), NULL, 10);
        }
    }
    LogI("[HeaderField::GetContentLength()] No such field: Content-Length")
    return 0;
}

void HeaderField::ToString(std::string &_target) {
    _target.clear();
    for (auto entry = header_fields_.begin(); entry != header_fields_.end(); entry++) {
        _target += entry->first;
        _target += ":";
        _target += entry->second;
        _target += "\r\n";
    }
}

bool HeaderField::ParseFromString(std::string &_target) {
    std::string::size_type pos = 0;
    while ((pos = _target.find("\r\n"), pos) != std::string::npos) {
    
    }
    return true;
}

void HeaderField::AppendToBuffer(AutoBuffer &_out_buff) {
    std::string str;
    ToString(str);
    _out_buff.Write(str.data(), str.size());
}


}

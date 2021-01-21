#include "requestline.h"
#include <string.h>
#include "../log.h"
#include "../strutil.h"


namespace http {

RequestLine::RequestLine()
    : method_(kPOST)
    , version_(kHTTP_1_1)
    , url_() {}
    
    
const char *const RequestLine::method2string[] = {
        "unknown",
        "GET",
        "POST",
        "DELETE",
};

const char *const RequestLine::version2string[] = {
        "unknown",
        "HTTP/0.9",
        "HTTP/1.0",
        "HTTP/1.1",
        "HTTP/2.0",
};
    
void RequestLine::SetMethod(THttpMethod _method) { method_ = _method; }

void RequestLine::SetUrl(const std::string &_url) { url_ = _url; }

void RequestLine::SetVersion(THttpVersion _version) { version_ = _version; }

void RequestLine::ToString(std::string &_target) {
    _target.clear();
    _target += method2string[method_];
    _target += " ";
    _target += url_;
    _target += " ";
    _target += version2string[version_];
    _target += "\r\n";
}

bool RequestLine::ParseFromString(std::string &_from) {
    std::vector<std::string> res;
    oi::split(_from, " ", res);
    if (res.size() != 3) {
        LogI("[RequestLine::ParseFromString] res.size(): %ld", res.size())
        return false;
    }
    method_ = __GetHttpMethod(res[0]);
    url_ = res[1];
    version_ = __GetHttpVersion(res[2]);
    return true;
}

void RequestLine::AppendToBuffer(AutoBuffer &_buffer) {
    std::string str;
    ToString(str);
    _buffer.Write(str.data(), str.size());
}

RequestLine::THttpMethod RequestLine::__GetHttpMethod(const std::string &_str) {
    for (int i = 0; i < kMethodMax; i++) {
        if (strcmp(_str.c_str(), method2string[i]) == 0) {
            return (THttpMethod) i;
        }
    }
    return kUnknownMethod;
}

RequestLine::THttpVersion RequestLine::__GetHttpVersion(const std::string &_str) {
    for (int i = 0; i < kVersionMax; ++i) {
        if (strcmp(_str.c_str(), version2string[i]) == 0) {
            return (THttpVersion) i;
        }
    }
    return kUnknownVer;
}

}

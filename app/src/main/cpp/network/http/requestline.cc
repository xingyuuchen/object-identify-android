#include "requestline.h"
#include <string.h>


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
    std::string::size_type space1 = _from.find(" ");
    if (space1 != std::string::npos) {
        method_ = __GetHttpMethod(_from.substr(0, space1));
        std::string::size_type space2 = _from.find(" ", space1 + 1);
        if (space2!= std::string::npos) {
            url_ = _from.substr(space1, space2);
            version_ = __GetHttpVersion(_from.substr(space2, _from.size()));
            return true;
        }
    }
    return false;
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

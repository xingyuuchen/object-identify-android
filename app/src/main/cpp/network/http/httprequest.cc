#include "httprequest.h"
#include "firstline.h"
#include "headerfield.h"
#include "../utils/log.h"
#include <string.h>
#include "../utils/strutil.h"


namespace http { namespace request {


void Pack(const std::string &_host, const std::string &_url, const std::map<std::string, std::string> &_headers,
          AutoBuffer& _send_body, AutoBuffer &_out_buff) {
    _out_buff.Reset();
    
    RequestLine request_line;
    request_line.SetMethod(http::kPOST);
    request_line.SetVersion(http::kHTTP_1_1);
    request_line.SetUrl(_url);
    request_line.AppendToBuffer(_out_buff);
    
    HeaderField header_field;
    for (auto iter = _headers.begin(); iter != _headers.end(); iter++) {
        header_field.InsertOrUpdateHeader(iter->first, iter->second);
    }
    header_field.InsertOrUpdateHeader(HeaderField::KHost, _host);
    header_field.InsertOrUpdateHeader(HeaderField::KConnection, HeaderField::KConnectionClose);
    
    char len_str[9] = {0, };
    snprintf(len_str, sizeof(len_str), "%zu", _send_body.Length());
    header_field.InsertOrUpdateHeader(HeaderField::KContentLength, len_str);
    
    header_field.AppendToBuffer(_out_buff);
    _out_buff.Write(_send_body.Ptr(), _send_body.Length());
    
}


Parser::Parser()
    : position_(TPosition::kNone)
    , resolved_len_(0)
    , request_line_len_(0)
    , request_header_len_(0) {}
        

void Parser::__ResolveRequestLine(AutoBuffer &_buff) {
    char *start = _buff.Ptr();
    char *crlf = oi::strnstr(start, "\r\n", _buff.Length());
    if (crlf != NULL) {
        std::string req_line(start, crlf - start);
        if (request_line_.ParseFromString(req_line)) {
            position_ = kRequestHeaders;
            resolved_len_ = crlf - start + 2;   // 2 for CRLF
            request_line_len_ = resolved_len_;
    
            if (_buff.Length() > resolved_len_) {
                __ResolveRequestHeaders(_buff);
            }
            return;
            
        } else {
            position_ = kError;
        }
    }
    resolved_len_ = 0;
}

void Parser::__ResolveRequestHeaders(AutoBuffer &_buff) {
    char *ret = oi::strnstr(_buff.Ptr(resolved_len_),
                    "\r\n\r\n", _buff.Length() - resolved_len_);
    if (ret == NULL) { return; }
    
    std::string headers_str(_buff.Ptr(resolved_len_), ret - _buff.Ptr(resolved_len_));
    
    if (headers_.ParseFromString(headers_str)) {
        resolved_len_ += ret - _buff.Ptr(resolved_len_) + 4;  // 4 for \r\n\r\n
        request_header_len_ = resolved_len_ - request_line_len_;
        position_ = kBody;
        
        if (_buff.Length() > resolved_len_) {
            __ResolveBody(_buff);
        }
    } else {
        position_ = kError;
        LogE("[__ResolveRequestHeaders] headers_.ParseFromString Err")
    }
}

void Parser::__ResolveBody(AutoBuffer &_buff) {
    uint64_t content_length = headers_.GetContentLength();
    if (content_length == 0) {
        LogI("[req::Parser::Recv] content_length = 0")
        position_ = kError;
        return;
    }
    size_t new_size = _buff.Length() - resolved_len_;
    body_.Write(_buff.Ptr(resolved_len_), new_size);
    resolved_len_ += new_size;
    
    if (content_length < body_.Length()) {
        LogI("[req::Parser::__ResolveBody] recv more %zd bytes than Content-Length(%lld)",
             body_.Length(), content_length)
        position_ = kError;
    } else if (content_length == body_.Length()) {
        position_ = kEnd;
    }

}


void Parser::Recv(AutoBuffer &_buff) {
    if (_buff.Length() <= 0) { return; }
    size_t unresolved_len = _buff.Length() - resolved_len_;
    if (unresolved_len <= 0) {
        LogI("[req::Parser::Recv] no bytes need to be resolved: %zd", unresolved_len)
        return;
    }
    
    if (position_ == kNone) {
        LogI("[req::Parser::Recv] kNone")
        if (resolved_len_ == 0 && _buff.Length() > 0) {
            position_ = kRequestLine;
            __ResolveRequestLine(_buff);
        }
        
    } else if (position_ == kRequestLine) {
        LogI("[req::Parser::Recv] kRequestLine")
        __ResolveRequestLine(_buff);
    
    } else if (position_ == kRequestHeaders) {
        LogI("[req::Parser::Recv] kRequestHeaders")
        __ResolveRequestHeaders(_buff);
    
    } else if (position_ == kBody) {
        __ResolveBody(_buff);
    
    } else if (position_ == kEnd) {
        LogI("[req::Parser::Recv] kEnd")
    
    } else if (position_ == kError) {
        LogI("[req::Parser::Recv] error already occurred, do nothing.")
    }
}


bool Parser::IsEnd() const { return position_ == kEnd; }

bool Parser::IsErr() const { return position_ == kError; }

Parser::TPosition Parser::GetPosition() const { return position_; }

AutoBuffer &Parser::GetBody() { return body_; }

}}


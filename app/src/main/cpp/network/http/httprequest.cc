#include "httprequest.h"
#include "requestline.h"
#include "headerfield.h"
#include "../log.h"
#include <string.h>
#include "../strutil.h"


namespace http { namespace request {


void Pack(const std::string &_host, const std::string &_url, const std::map<std::string, std::string> _headers,
          AutoBuffer& _send_body, AutoBuffer &_out_buff) {
    _out_buff.Reset();
    
    RequestLine request_line;
    request_line.SetMethod(http::RequestLine::kPOST);
    request_line.SetVersion(http::RequestLine::kHTTP_1_1);
    request_line.SetUrl(_url);
    request_line.AppendToBuffer(_out_buff);
    
    HeaderField header_field;
    for (auto iter = _headers.begin(); iter != _headers.end(); iter++) {
        header_field.InsertOrUpdateHeader(iter->first, iter->second);
    }
    header_field.InsertOrUpdateHeader(HeaderField::KHost, _host);
    header_field.InsertOrUpdateHeader(HeaderField::KConnection, HeaderField::KConnectionClose);
    
    char len_str[8] = {0, };
    snprintf(len_str, sizeof(len_str), "%zu", _send_body.Length());
    header_field.InsertOrUpdateHeader(HeaderField::KContentLength, len_str);
    
    header_field.AppendToBuffer(_out_buff);
}


Parser::Parser()
    : position_(TPosition::kNone)
    , request_line_ok_(false) {}
        

void Parser::Recv(AutoBuffer &_buff) {
    if (_buff.Length() <= 0) { return; }
    size_t unresolved_len = _buff.Length() - resolved_len_;
    if (unresolved_len <= 0) {    // FIXME +1 -1
        LogI("[Parser::Recv] no bytes need to be resolved: %zd", unresolved_len)
        return;
    }
    
    if (position_ == kNone) {
        LogI("[Parser::Recv] kNone")
        if (resolved_len_ == 0 && _buff.Length() > 0) {
            position_ = kRequestLine;
            Recv(_buff);
            return;
        }
        
    } else if (position_ == kRequestLine) {
        LogI("[Parser::Recv] kRequestLine")
        char *start = _buff.Ptr();
        char *ret = oi::strnstr(start, "\r\n", unresolved_len);
        if (ret != NULL) {
            std::string req_line(start, ret - start);
            if (request_line_.ParseFromString(req_line)) {
                request_line_ok_ = true;
                position_ = kRequestHeaders;
                resolved_len_ = ret - start;
                Recv(_buff);
                return;
            } else {
                position_ = kError;
                resolved_len_ = 0;  // parse next time
                return;
            }
        }
    
    } else if (position_ == kRequestHeaders) {
        LogI("[Parser::Recv] kRequestHeaders")
        char *ret = oi::strnstr(_buff.Ptr(resolved_len_), "\r\n\r\n", unresolved_len);
        if (ret == NULL) {
            return;
        }
    
        std::string headers_str(_buff.Ptr(resolved_len_), unresolved_len);
    
        if (headers_.ParseFromString(headers_str)) {
            resolved_len_ += unresolved_len;
            position_ = kBody;
            Recv(_buff);
            return;
        } else {
            position_ = kError;
            return;
        }
    
    } else if (position_ == kBody) {
        uint64_t content_length = headers_.GetContentLength();
        if (content_length == 0) {
            LogI("[Parser::Recv] content_length = 0")
            position_ = kError;
            return;
        }
        body_.Write(_buff.Ptr(resolved_len_), unresolved_len);
        if (content_length < body_.Length()) {
            LogI("[Parser::Recv] recv more %zd bytes than Content-Length(%lld)",
                body_.Length(), content_length)
            position_ = kError;
            return;
        } else if (content_length == body_.Length()) {
            position_ = kEnd;
            return;
        }
        resolved_len_ += unresolved_len;
    
    } else if (position_ == kEnd) {
        LogI("[Parser::Recv] kEnd")
    
    } else if (position_ == kError) {
        LogI("[Parser::Recv] error already occurred, do nothing.")
    }
}


bool Parser::IsEnd() const { return position_ == kEnd; }

bool Parser::IsErr() const { return position_ == kError; }

Parser::TPosition Parser::GetPosition() const { return position_; }

AutoBuffer &Parser::GetBody() { return body_; }

}}


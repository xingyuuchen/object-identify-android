#ifndef OI_SVR_REQUESTLINE_H
#define OI_SVR_REQUESTLINE_H

#include <string>
#include "../autobuffer.h"


namespace http {

class RequestLine {
  public:
    enum THttpMethod {
        kUnknownMethod = 0,
        kGET,
        kPOST,   // only support post
        kDELETE,
        // ...
        kMethodMax,
    };
    static const char *const method2string[kMethodMax];
    
    enum THttpVersion {
        kUnknownVer = 0,
        kHTTP_0_9,
        kHTTP_1_0,
        kHTTP_1_1,   // only support 1.1
        kHTTP_2_0,
        kVersionMax,
    };
    static const char *const version2string[kVersionMax];
    

  public:
    
    RequestLine();
    
    void SetUrl(const std::string &_url);
    
    void SetMethod(THttpMethod _method);
    
    void SetVersion(THttpVersion _version);
    
    void ToString(std::string &_target);
    
    bool ParseFromString(std::string &_from);
    
    void AppendToBuffer(AutoBuffer &_buffer);
    
    static THttpMethod __GetHttpMethod(const std::string &_str);
    
    static THttpVersion __GetHttpVersion(const std::string &_str);
    

  private:
    THttpMethod method_;
    std::string url_;
    THttpVersion version_;
    
};

}

#endif //OI_SVR_REQUESTLINE_H

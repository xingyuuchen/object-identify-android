#ifndef OI_SHORTLINK_H
#define OI_SHORTLINK_H

#include "task.h"
#include "socket/unix_socket.h"
#include "utils/autobuffer.h"
#include <functional>
#include <memory>
#include <future>
#include <atomic>


class ShortLink {
  public:
    enum TStatus {
        kNotStart = 0,
        kRunning,
        kFinished,
        kError,
    };

    using AtomicStatus = std::atomic<int>;

    explicit ShortLink(Task &_task, std::string _svr_inet_addr = "127.0.0.1",
            u_short _port = 5002);

    ~ShortLink();

    void DoConnect();

    AutoBuffer &GetSendBody();

    AutoBuffer &GetRecvBody();

    int DoTask();

    int GetNetId() const;

    int GetStatus() const;

    bool IsInvalid() const;

    bool StatusErr() const;

    bool CanRetry() const;

    u_short GetPort() const;

    int GetErrCode() const;

    std::string &GetCgi();

    std::string &GetHost();

  private:
    void __ReadWrite();
    int __Run();
    void __WaitForWorker();

  private:
    static const char *const    TAG;
    AtomicStatus                status_;
    std::atomic_int             curr_retry_cnt_;
    static const size_t         kRecvBuffSize;
    static const size_t         kSendBuffSize;
    Task                        task_;
    int                         err_code_;
    SOCKET                      socket_;
    AutoBuffer                  send_body_;
    AutoBuffer                  recv_body_;
    u_short                     port_;
    std::string                 svr_inet_addr_;
    std::future<int>            async_ret_;
    std::shared_ptr<std::function<void (long, long)>> progress_cb_;

};


#endif //OI_SHORTLINK_H

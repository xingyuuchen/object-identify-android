#ifndef TASK_H_
#define TASK_H_

#include <stdlib.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <string>


class Task {
  private:

  public:
    int type_;
    int retry_cnt_;
    std::string cgi_;
    Task() {}

};


#endif  // TASK_H_

#ifndef TASK_H_
#define TASK_H_

#include <string>


class Task {
  private:

  public:
    int netid_;
    int retry_cnt_;
    std::string cgi_;
    bool care_about_progress_;

};


#endif  // TASK_H_

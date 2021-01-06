#ifndef TASK_H_
#define TASK_H_
#include <stdlib.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>


class Task {
  private:
    int type_;
    int cmd_id_;

  public:
    Task() {}
    explicit Task(int _type, int _cmd_id)
        : type_(_type)
        , cmd_id_(_cmd_id) {

    }

    void DoSomeThing() const {
        int a = type_;
        a++;
        // ERROR: type_++;
    }

    const void SetCmdId(int _cmd_id) {
        this->cmd_id_ = _cmd_id;
    }

    void SetType(int _type) {
        this->type_ = _type;
    }

    int GetType() const {
        return type_;
    }

};


#endif  // TASK_H_

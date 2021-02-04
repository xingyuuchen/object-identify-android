#ifndef OI_THREAD_H
#define OI_THREAD_H

#include <pthread.h>
#include <string>
#include <unistd.h>
#include "utils/log.h"


class Runnable {
public:
    virtual void Run() = 0;
    virtual ~Runnable() {}
};

template<class T>
class RunnableFunctor : public Runnable {
  public:

    RunnableFunctor(const T& _f) : func(_f) {}

    ~RunnableFunctor() {
    }

    void Run() {
        func();
    }

  private:
    T func;
};


class Thread {
  public:

    template<class T>
    explicit Thread(const T& _op)
            : runnable_(nullptr), tid_(0), attr_() {

        pthread_attr_init(&attr_);
        runnable_ = new RunnableFunctor<T>(_op);

    }

    explicit Thread() {};

    ~Thread() {
        delete runnable_;
    }


    void Start();

    pthread_t GetTid() const;

    static void *StartRoutine(void *_arg) {
        Runnable *runnable = static_cast<Runnable *>(_arg);
        runnable->Run();
        return 0;
    }

  private:
    Runnable* runnable_;
    pthread_t tid_;
    pthread_attr_t attr_;


};

#endif //OI_THREAD_H

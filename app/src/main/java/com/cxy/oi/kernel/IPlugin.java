package com.cxy.oi.kernel;


/**
 *  note:
 *
 *      每一个对外暴露的接口类需实现此接口,
 *      并去 {@link com.cxy.oi.kernel.DefaultBootStep} 中进行注册
 *
 */
public interface IPlugin {

    void doWhenBoot();

}

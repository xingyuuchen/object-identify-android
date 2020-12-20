package com.cxy.oi.kernel;


/**
 *  note:
 *
 *      每一个对外暴露的接口类需 extends 此接口,
 *      并去 {@link com.cxy.oi.kernel.DefaultBootStep} 中进行注册
 *
 *      如：
 *          {@link com.cxy.oi.plugin_storage.IPluginStorage}, 其实现类为
 *          {@link com.cxy.oi.plugin_storage.PluginStorage}。
 *
 *          外界调用其暴露的接口时：OIKernel.plugin(IPluginStorage.class).someMethod();
 */
public interface IPlugin {

    void doWhenBoot();

}

package com.cxy.oi.app.netscene

import com.cxy.oi.kernel.contants.ConstantsProtocol
import com.cxy.oi.kernel.modelbase.NetSceneBase
import com.cxy.oi.kernel.network.IDispatcher

class NetSceneUploadAvatar : NetSceneBase() {
    private val TAG = "NetSceneUploadAvatar"


    override fun doScene(dispatcher: IDispatcher?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(): Int {
        return ConstantsProtocol.NETSCENE_TYPE_UPLOAD_AVATAR
    }

    override fun getTag(): String {
        return TAG
    }
}
package com.ldt.BeatHive.helper.extension

import com.ldt.BeatHive.notification.EventKey
import com.zalo.gitlabmobile.notification.MessageEvent
import org.greenrobot.eventbus.EventBus

fun EventKey.post(data: Any? = null, subData: Any? = null, subSubData: Any? = null) {
    postEvent(this, data, subData, subSubData)
}

fun postEvent(eventKey: EventKey, data: Any? = null, subData: Any? = null, subSubData: Any? = null) {
    EventBus.getDefault().post(MessageEvent(eventKey, data, subData, subSubData))
}
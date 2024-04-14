package com.zalo.gitlabmobile.notification

import com.ldt.BeatHive.notification.EventKey

class MessageEvent(val key: EventKey, val data: Any? = null, val subData: Any? = null, val subSubData: Any? = null)
package ru.dev.gamedev.honest_investor

import ru.dev.gamedev.honest_investor.repository.AppsData
const val key = "M2HZt64nQp8gxAmhzkTMm8"
const val titleNotifi = "Честный инвестор"
const val subTitleNotifi = "Текст уведомления"


var appsData : AppsData = AppsData()

const val EMPTY = ""
enum class StaticKeys(val key : String){
    CUID("cuid"),
}

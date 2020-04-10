package com.gerka.okhttpcache.bean

import java.io.Serializable

data class ListResponse(
    var errorCode: Int = 0,
    var errorMsg: String?,
    var data: List<Data>
) {
    data class Data(
        var children: List<Children>?,
        var courseId: Int,
        var id: Int,
        var name: String,
        var order: Int,
        var parentChapterId: Int,
        var userControlSetTop: Boolean,
        var visible: Int
    ) : Serializable {

        data class Children(
            var id: Int,
            var name: String,
            var courseId: Int,
            var parentChapterId: Int,
            var order: Int,
            var visible: Int,
            var children: List<Children>?
        ) : Serializable

    }
}
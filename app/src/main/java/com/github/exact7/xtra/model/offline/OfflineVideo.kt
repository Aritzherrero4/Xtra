package com.github.exact7.xtra.model.offline

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "videos")
data class OfflineVideo(
        val url: String,
//        val sourceUrl: String,
        val name: String,
        @ColumnInfo(name = "channel_name")
        val channelName: String,
        @ColumnInfo(name = "channel_logo")
        val channelLogo: String,
        val thumbnail: String,
        val game: String,
        val duration: Long,
        @ColumnInfo(name = "upload_date")
        val uploadDate: Long,
        @ColumnInfo(name = "download_date")
        val downloadDate: Long) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @IgnoredOnParcel
    @ColumnInfo(name = "is_vod")
    var vod = url.endsWith(".m3u8")

    @IgnoredOnParcel
    var downloaded = false //TODO add progress and update in interval in ui
}

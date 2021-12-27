package com.github.andreyasadchy.xtra.model.offline

interface Downloadable {
    val id: String
    val title: String?
    val thumbnail: String?
    val channelId: String?
    val channelName: String?
    val channelLogo: String?
    val gameId: String?
    val gameName: String?
    val uploadDate: String?
    val videoType: String?
}

internal class Wrapper(downloadable: Downloadable) : Downloadable {

    override val id: String
    override val title: String?
    override val thumbnail: String?
    override val channelId: String?
    override val channelName: String?
    override val channelLogo: String?
    override val gameId: String?
    override val gameName: String?
    override val uploadDate: String?
    override val videoType: String?

    init {
        downloadable.let {
            id = it.id
            title = it.title
            thumbnail = it.thumbnail
            channelId = it.channelId
            channelName = it.channelName
            channelLogo = it.channelLogo
            gameId = it.gameId
            gameName = it.gameName
            uploadDate = it.uploadDate
            videoType = it.videoType
        }
    }
}
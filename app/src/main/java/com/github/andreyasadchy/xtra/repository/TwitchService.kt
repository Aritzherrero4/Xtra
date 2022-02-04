package com.github.andreyasadchy.xtra.repository

import com.github.andreyasadchy.xtra.model.chat.CheerEmote
import com.github.andreyasadchy.xtra.model.chat.TwitchEmote
import com.github.andreyasadchy.xtra.model.chat.VideoMessagesResponse
import com.github.andreyasadchy.xtra.model.helix.channel.ChannelSearch
import com.github.andreyasadchy.xtra.model.helix.clip.Clip
import com.github.andreyasadchy.xtra.model.helix.follows.Follow
import com.github.andreyasadchy.xtra.model.helix.game.Game
import com.github.andreyasadchy.xtra.model.helix.stream.Stream
import com.github.andreyasadchy.xtra.model.helix.user.User
import com.github.andreyasadchy.xtra.model.helix.video.BroadcastType
import com.github.andreyasadchy.xtra.model.helix.video.Sort
import com.github.andreyasadchy.xtra.model.helix.video.Video
import com.github.andreyasadchy.xtra.type.ClipsPeriod
import com.github.andreyasadchy.xtra.type.VideoSort
import kotlinx.coroutines.CoroutineScope

interface TwitchService {

    fun loadTopGames(clientId: String?, userToken: String?, coroutineScope: CoroutineScope): Listing<Game>
    suspend fun loadStream(clientId: String?, userToken: String?, channelId: String): Stream?
    fun loadTopStreams(clientId: String?, userToken: String?, gameId: String?, languages: String?, thumbnailsEnabled: Boolean, coroutineScope: CoroutineScope): Listing<Stream>
    fun loadFollowedStreams(useHelix: Boolean, gqlClientId: String?, helixClientId: String?, userToken: String?, userId: String, thumbnailsEnabled: Boolean, coroutineScope: CoroutineScope): Listing<Stream>
    fun loadClips(clientId: String?, userToken: String?, channelId: String?, channelLogin: String?, gameId: String?, started_at: String?, ended_at: String?, coroutineScope: CoroutineScope): Listing<Clip>
    suspend fun loadVideo(clientId: String?, userToken: String?, videoId: String): Video?
    fun loadVideos(clientId: String?, userToken: String?, gameId: String?, period: com.github.andreyasadchy.xtra.model.helix.video.Period, broadcastType: BroadcastType, language: String?, sort: Sort, coroutineScope: CoroutineScope): Listing<Video>
    fun loadChannelVideos(clientId: String?, userToken: String?, channelId: String, period: com.github.andreyasadchy.xtra.model.helix.video.Period, broadcastType: BroadcastType, sort: Sort, coroutineScope: CoroutineScope): Listing<Video>
    suspend fun loadUserById(clientId: String?, userToken: String?, id: String): User?
    fun loadSearchGames(clientId: String?, userToken: String?, query: String, coroutineScope: CoroutineScope): Listing<Game>
    fun loadSearchChannels(clientId: String?, userToken: String?, query: String, coroutineScope: CoroutineScope): Listing<ChannelSearch>
    suspend fun loadUserFollows(clientId: String?, userToken: String?, userId: String, channelId: String): Boolean
    fun loadFollowedChannels(clientId: String?, userToken: String?, userId: String, coroutineScope: CoroutineScope): Listing<Follow>
    suspend fun loadEmotesFromSet(clientId: String?, userToken: String?, setIds: List<String>): List<TwitchEmote>?
    suspend fun loadCheerEmotes(clientId: String?, userToken: String?, userId: String): List<CheerEmote>?
    suspend fun loadVideoChatLog(clientId: String?, videoId: String, offsetSeconds: Double): VideoMessagesResponse
    suspend fun loadVideoChatAfter(clientId: String?, videoId: String, cursor: String): VideoMessagesResponse

    suspend fun loadStreamGQL(clientId: String?, channelId: String): Stream?
    suspend fun loadVideoGQL(clientId: String?, videoId: String): Video?
    suspend fun loadUserByIdGQL(clientId: String?, channelId: String): User?
    suspend fun loadCheerEmotesGQL(clientId: String?, userId: String): List<CheerEmote>?
    fun loadTopGamesGQL(clientId: String?, coroutineScope: CoroutineScope): Listing<Game>
    fun loadTopStreamsGQL(clientId: String?, thumbnailsEnabled: Boolean, coroutineScope: CoroutineScope): Listing<Stream>
    fun loadTopVideosGQL(clientId: String?, coroutineScope: CoroutineScope): Listing<Video>
    fun loadGameStreamsGQL(clientId: String?, gameId: String?, coroutineScope: CoroutineScope): Listing<Stream>
    fun loadGameVideosGQL(clientId: String?, gameId: String?, type: com.github.andreyasadchy.xtra.type.BroadcastType?, sort: VideoSort?, coroutineScope: CoroutineScope): Listing<Video>
    fun loadGameClipsGQL(clientId: String?, gameId: String?, sort: ClipsPeriod?, coroutineScope: CoroutineScope): Listing<Clip>
    fun loadChannelVideosGQL(clientId: String?, channelId: String?, type: com.github.andreyasadchy.xtra.type.BroadcastType?, sort: VideoSort?, coroutineScope: CoroutineScope): Listing<Video>
    fun loadChannelClipsGQL(clientId: String?, channelId: String?, sort: ClipsPeriod?, coroutineScope: CoroutineScope): Listing<Clip>
    fun loadSearchChannelsGQL(clientId: String?, query: String, coroutineScope: CoroutineScope): Listing<ChannelSearch>
    fun loadSearchGamesGQL(clientId: String?, query: String, coroutineScope: CoroutineScope): Listing<Game>
}

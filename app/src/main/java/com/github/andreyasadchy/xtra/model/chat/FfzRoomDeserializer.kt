package com.github.andreyasadchy.xtra.model.chat

import com.github.andreyasadchy.xtra.ui.view.chat.emoteQuality
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class FfzRoomDeserializer : JsonDeserializer<FfzEmotesResponse> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): FfzEmotesResponse {
        val emotes = mutableListOf<FfzEmote>()
        for (setEntry in json.asJsonObject.getAsJsonObject("sets").entrySet()) {
            val emotesArray = setEntry.value.asJsonObject.getAsJsonArray("emoticons")
            for (i in 0 until emotesArray.size()) {
                val emote = emotesArray.get(i).asJsonObject
                val urls = emote.getAsJsonObject("urls")
                val quality = urls.get(when (emoteQuality) {"3" -> ("4") "2" -> ("2") else -> ("1")})
                val url = "https:" + (quality.takeUnless { it?.isJsonNull == true }?.asString ?: urls.get("2").takeUnless { it?.isJsonNull == true }?.asString ?: urls.get("1").asString)
                emotes.add(FfzEmote(emote.get("name").asString, url))
            }
        }
        return FfzEmotesResponse(emotes)
    }
}
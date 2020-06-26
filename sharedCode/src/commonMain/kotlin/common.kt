package com.jetbrains.handson.mpp.mobile

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

fun generateRandomPastelColor(): RGBColor {
    val red = Random.nextInt(127, 255)
    val green = Random.nextInt(127, 255)
    val blue = Random.nextInt(127, 255)
    return RGBColor(red, green, blue)
}

data class RGBColor (
    val red: Int,
    val green: Int,
    val blue: Int
)

data class GifType(
    val title: String,
    val keyword: String,
    val color: RGBColor
)

@Serializable
data class GiphyData(
    val data: List<Gif>
)

@Serializable
data class Gif(
    val images: GifImages
)

@Serializable
data class GifImages(
    @SerialName("fixed_width")
    val previewImage: GifImage
)

@Serializable
data class GifImage (
    val url: String,
    val height: Int,
    val width: Int
)

class GifService: CoroutineScope {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict)
        }
    }

    override val coroutineContext: CoroutineContext = dispatcher() + SupervisorJob()

    companion object {
        private const val baseUrl = "https://api.giphy.com/v1/gifs"
        private const val apiKey = "v3hqiHjLrJw9qGaQLAph7HCexWT34IkH"
        private const val limit = 50
    }

    val availableTypes = listOf(
        GifType("🐱", "kitten", generateRandomPastelColor()),
        GifType("🐶", "puppy", generateRandomPastelColor()),
        GifType("🦜", "parrot", generateRandomPastelColor()),
        GifType("🙊", "baby monkey", generateRandomPastelColor()),
        GifType("🐢", "baby turtle", generateRandomPastelColor()),
        GifType("🦉", "baby owl", generateRandomPastelColor())
    )

    suspend fun getGifs(types: List<GifType>): List<Gif> {
        val queryLimit = limit / types.count()
        return types.map {
            client.get<GiphyData> {
                url("${GifService.baseUrl}/search?api_key=${GifService.apiKey}&q=${it.keyword}&limit=${queryLimit}")
            }.data
        }.flatten().shuffled()
    }
}


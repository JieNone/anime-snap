package ru.tyurin.animesnap.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTitle(
    @SerialName("frameCount") val frameCount: Int,
    @SerialName("error") val error: String,
    @SerialName("result") val result: List<Result>
)

@Serializable
data class Result(
    val similarity: Double,
    @SerialName(value = "image") val imgSrc: String,
    val anilist: Int,
    val filename: String,
    val episode: Int,
    val from: Double,
    val to: Double,
    val video: String,
)

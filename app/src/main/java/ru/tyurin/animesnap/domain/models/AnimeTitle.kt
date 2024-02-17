package ru.tyurin.animesnap.domain.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//
//@Serializable
//data class AnimeTitle(
//    @SerialName("frameCount") val frameCount: Int,
//    @SerialName("error") val error: String,
//    @SerialName("result") val result: List<Result>
//)
//
//@Serializable
//data class Result(
//    val similarity: Double?,
//    @SerialName(value = "image") val imgSrc: String?    ,
//    val anilist: Int?,
//    val filename: String?,
//    val episode: Float?,
//    val from: Double?,
//    val to: Double?,
//    val video: String?,
//)

@Serializable
data class Title(
    val native: String,
    val romaji: String,
    val english: String
)

@Serializable
data class Anilist(
    val id: Int,
    val idMal: Int,
    val title: Title,
    val synonyms: List<String>,
    val isAdult: Boolean
)

@Serializable
data class Result(
    val anilist: Anilist,
    val filename: String,
    val episode: Int,
    val from: Double,
    val to: Double,
    val similarity: Double,
    val video: String,
    @SerialName(value = "image") val imgSrc: String?
)

@Serializable
data class AnimeTitle(
    val frameCount: Int,
    val error: String,
    val result: List<Result>
)

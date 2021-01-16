package com.example.satya.data.database

import com.satya.pretest.model.Result

fun DataEntity.toData() = Result(
//    this.airing,
//    this.episodes,
    this.image_url,
//    this.mal_id,
//    this.members,
//    this.rated,
//    this.score,
//    this.start_date,
//    this.end_date!!,
    this.synopsis,
    this.title,
    this.type
//    this.url
)


fun List<DataEntity>.toDataList() = this.map {
    it.toData()
}

fun Result.toDataEntity() = DataEntity(
//    airing = this.airing,
//    episodes = this.episodes,
    image_url = this.image_url,
//    mal_id = this.mal_id,
//    members = this.members,
//    rated = this.rated,
//    score = this.score,
//    start_date = this.start_date,
//    end_date = this.end_date,
    synopsis = this.synopsis,
    title = this.title,
    type = this.type
//    url = this.url
)



fun List<Result>.toDataEntityList() = this.map { it.toDataEntity() }
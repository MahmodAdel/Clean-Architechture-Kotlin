package com.example.data.model

import androidx.room.Entity
import com.example.data.base.EntityMapper
import com.example.data.base.ModelEntity
import com.example.domain.model.Car
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

@Entity(tableName = "car", primaryKeys = ["id"])
data class CarEntity(
    @field:Expose @field:SerializedName("id") var id: Int,
    @field:Expose @field:SerializedName("albumId") var albumId: Int,
    @field:Expose @field:SerializedName("title") var title: String,
    @field:Expose @field:SerializedName("url") var url: String,
    @field:Expose @field:SerializedName("thumbnailUrl") var thumbnailUrl: String
):ModelEntity()

class CarEntityMapper @Inject constructor():EntityMapper<Car,CarEntity>{
    override fun mapToDomain(entity: CarEntity): Car= Car(id = entity.id,
    title = if(entity.title == null) "" else entity.title,
    url = if(entity.url == null) "" else entity.url,
    albumId = entity.albumId,
    thumbnailUrl = if(entity.thumbnailUrl == null) "" else entity.thumbnailUrl)

    override fun mapToEntity(model: Car): CarEntity= CarEntity(
        id = model.id,
        albumId = model.albumId,
        title = model.title,
        url = model.url,
        thumbnailUrl = model.thumbnailUrl
    )

}

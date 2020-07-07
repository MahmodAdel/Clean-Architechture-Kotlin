package com.example.cleanarchitecture.model

import com.example.cleanarchitecture.base.ItemMapper
import com.example.cleanarchitecture.base.ModelItem
import com.example.domain.model.Car
import com.example.domain.model.Model
import javax.inject.Inject

data class CarItem(val id: Int,val albumId: Int,val title:String, val url: String,val thumbnailUrl: String)
    :ModelItem()

class CarItemMapper  @Inject constructor() : ItemMapper<Car, CarItem> {
    override fun mapToPresentation(model: Car)= CarItem (
        model.id,model.albumId,model.title,model.url,model.thumbnailUrl
    )

    override fun mapToDomain(modelItem: CarItem)= Car (
         modelItem.id,modelItem.albumId,modelItem.title,modelItem.url,modelItem.thumbnailUrl

    )


}
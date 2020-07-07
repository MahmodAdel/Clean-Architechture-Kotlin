package com.example.cleanarchitecture.model

import android.os.Parcelable
import com.example.cleanarchitecture.base.ItemMapper
import com.example.cleanarchitecture.base.ModelItem
import com.example.domain.model.Car
import com.example.domain.model.Model
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject
@Parcelize
data class RepoItem(val id: Int,val albumId: Int,val title:String, val url: String,val thumbnailUrl: String):
    ModelItem(), Parcelable

class RepoItemMapper @Inject constructor():ItemMapper<Car,RepoItem>{
    override fun mapToPresentation(model: Car) =RepoItem(
        model.id,model.albumId,model.title,model.url,model.thumbnailUrl
    )


    override fun mapToDomain(modelItem: RepoItem)=Car(
        modelItem.id,modelItem.albumId,modelItem.title,modelItem.url,modelItem.thumbnailUrl
    )


}
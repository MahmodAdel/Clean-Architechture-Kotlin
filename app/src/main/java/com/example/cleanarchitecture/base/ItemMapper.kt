package com.example.cleanarchitecture.base

import com.example.domain.model.Car
import com.example.domain.model.Model


interface ItemMapper<M : Model, MI : ModelItem> {
    fun mapToPresentation(model: M): ModelItem

    fun mapToDomain(modelItem: MI): Model
}
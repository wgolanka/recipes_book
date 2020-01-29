package com.recipebook.domain.recipe.measurmentunit

import com.recipebook.domain.recipe.dto.MeasurementUnit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeasurementUnitRepository : JpaRepository<MeasurementUnit, Long> {
    fun getAllByIdIsNotNull(): List<MeasurementUnit>
    fun findByIdIs(id: UUID): MeasurementUnit?
    fun getMeasurementUnitByIdIs(id: UUID?): MeasurementUnit?
    fun deleteByIdEquals(id: UUID?)
}
package com.example.ddobagi3.model


data class JusoResponse(
    val documents: List<Documents>?,
    val meta: Meta?,
    val message: String?
)

data class Documents(
    val address_name: String?,
    val address_type: String?,
    val road_address: String?,
    val x: String?,
    val y: String?
)

data class Meta(
    val is_end: Boolean?,
    val pageable_count: Number?,
    val total_count: Number?
)
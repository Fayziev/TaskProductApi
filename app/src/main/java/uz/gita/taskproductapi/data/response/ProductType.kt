package uz.gita.taskproductapi.data.response

import com.google.gson.annotations.SerializedName

data class ProductType(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name_ru")
    val nameRu: String? = null,

    @field:SerializedName("name_uk")
    val nameUk: String? = null,

    @field:SerializedName("description")
    val description: Any? = null,

    @field:SerializedName("name_uz")
    val nameUz: String? = null
)

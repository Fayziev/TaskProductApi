package uz.gita.taskproductapi.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EditRequest(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("cost")
	val cost: Float? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("product_type_id")
	val productTypeId: Int? = null,

	@field:SerializedName("name_uz")
	val nameUz: String? = null,

	@field:SerializedName("created_date")
	val createdDate: Long? = null
)

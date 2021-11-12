package uz.gita.taskproductapi.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.taskproductapi.data.response.Product
import uz.gita.taskproductapi.databinding.ItemProductBinding
import java.text.SimpleDateFormat
import java.util.*

class ProductAdapter(private var list: List<Product>) : RecyclerView.Adapter<ProductAdapter.VH>() {
    private var listener: ((Product, View,Int) -> Unit)? = null

    inner class VH(var itemProductBinding: ItemProductBinding) : RecyclerView.ViewHolder(itemProductBinding.root) {
        private val type: StringBuilder = StringBuilder()
        private var typeInt = 1

        init {
            itemProductBinding.menuBtn.setOnClickListener {
                listener?.invoke(list[absoluteAdapterPosition], itemProductBinding.menuBtn,absoluteAdapterPosition)
            }

        }

        @SuppressLint("SimpleDateFormat")
        fun bind(product: Product) = with(itemProductBinding) {
            productName.text = product.nameUz
            productAddress.text = product.address
            typeInt = product.productTypeId!!
            when (typeInt) {
                1 -> {
                    type.delete(0, type.toString().length)
                    type.append("O'simliklar")
                }
                2 -> {
                    type.delete(0, type.toString().length)
                    type.append("Yovvoyi hayvonlar")
                }
                else -> {
                    type.delete(0, type.toString().length)
                    type.append("Daraxtlar")
                }
            }
            productTypes.text = type
            val date = Date(product.createdDate!!)
            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
            val textDate = simpleDateFormat.format(date)
            createdDate.text = textDate
            cost.text = product.cost.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(list[position])
    override fun getItemCount(): Int = list.size

    fun setListener(f: (Product, View,Int) -> Unit) {
        listener = f
    }

}
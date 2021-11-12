package uz.gita.taskproductapi.utils

import android.view.View
import android.widget.EditText
import androidx.core.widget.ContentLoadingProgressBar
import androidx.core.widget.addTextChangedListener

fun EditText.myAddTextChangeListener(block: (String) -> Unit) {
    this.addTextChangedListener {
        it?.let {
            block(it.toString())
        }
    }
}

fun EditText.textX() = this.text.toString()
fun ContentLoadingProgressBar.visible(bool: Boolean) {
    if (bool) this.show()
    else this.hide()
}

fun View.visible(bool: Boolean) {
    if (bool) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}
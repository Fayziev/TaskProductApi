package uz.gita.taskproductapi.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import timber.log.Timber

fun <T : ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}

fun timber(message: String, tag: String = "TTT") {
    Timber.tag(tag).d(message)
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}


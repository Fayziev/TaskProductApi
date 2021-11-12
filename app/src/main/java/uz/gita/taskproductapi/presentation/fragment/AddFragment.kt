package uz.gita.taskproductapi.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.taskproductapi.R
import uz.gita.taskproductapi.data.request.InsertRequest
import uz.gita.taskproductapi.databinding.FragmentAddBinding
import uz.gita.taskproductapi.utils.showToast
import uz.gita.taskproductapi.utils.visible
import uz.gita.taskproductapi.viewmodel.impl.AddProductViewModelImpl
import java.util.*

class AddFragment : Fragment(R.layout.fragment_add) {
    private val binding by viewBinding(FragmentAddBinding::bind)
    private val type: StringBuilder = StringBuilder()
    private var typeId = 1
    private val viewModel by viewModels<AddProductViewModelImpl>()

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.productTypeEditText.setOnClickListener {
            showPopUpMenu(it)
        }

        binding.addProduct.setOnClickListener {
            val name = binding.productNameEditText.text.toString()
            val address = binding.addressEditText.text.toString()
            val cost = binding.productCostEditText.text.toString()
            val type = binding.productTypeEditText.text.toString()

            if (name.isNotEmpty() && address.isNotEmpty() && cost.isNotEmpty() && type.isNotEmpty()) {
                val data = InsertRequest(cost.toFloat(), address, typeId, name, Calendar.getInstance().timeInMillis)
                viewModel.insertData(data)
            } else {
                showToast("Please fill in the blanks")
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.successMessageLiveData.observe(viewLifecycleOwner, successMessageObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.connectionLiveData.observe(viewLifecycleOwner, connectionObserver)
        viewModel.insertLiveData.observe(viewLifecycleOwner, insertLiveDataObserver)
    }

    private val errorMessageObserver = Observer<String> { showToast(it) }
    private val connectionObserver = Observer<String> { showToast(it) }
    private val successMessageObserver = Observer<String> { showToast(it) }

    private val progressObserver = Observer<Boolean> {
        binding.progress.visible(it)
    }
    private val insertLiveDataObserver = Observer<Unit> {
        findNavController().popBackStack()
    }

    private fun showPopUpMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.popupmenu2, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.usimliklar -> {
                    type.delete(0, type.toString().length)
                    type.append("O'simliklar")
                    typeId = 1
                    binding.productTypeEditText.setText(type.toString())
                }
                R.id.yovvoyi -> {
                    type.delete(0, type.toString().length)
                    type.append("Yovvoyi hayvonlar")
                    typeId = 2
                    binding.productTypeEditText.setText(type.toString())
                }
                R.id.daraxt -> {
                    type.delete(0, type.toString().length)
                    type.append("Daraxt")
                    typeId = 3
                    binding.productTypeEditText.setText(type.toString())
                }
            }
            true
        }
        popupMenu.show()
    }
}
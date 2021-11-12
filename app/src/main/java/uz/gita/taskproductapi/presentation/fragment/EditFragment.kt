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
import uz.gita.taskproductapi.data.request.EditRequest
import uz.gita.taskproductapi.data.response.Product
import uz.gita.taskproductapi.databinding.FragmentEditBinding
import uz.gita.taskproductapi.utils.showToast
import uz.gita.taskproductapi.utils.visible
import uz.gita.taskproductapi.viewmodel.impl.EditProductViewModelImpl
import java.util.*

class EditFragment : Fragment(R.layout.fragment_edit) {
    private val binding by viewBinding(FragmentEditBinding::bind)
    private val type: StringBuilder = StringBuilder()
    private var typeId = 1
    private val viewModel by viewModels<EditProductViewModelImpl>()

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val bundle = requireArguments()
        val data = bundle.getSerializable("data") as Product
        binding.productNameEditText.setText(data.nameUz.toString())
        binding.addressEditText.setText(data.address.toString())
        binding.productCostEditText.setText(data.cost.toString())
        binding.productTypeEditText.setText(data.productTypeId.toString())
        val id = data.id

        binding.productTypeEditText.setOnClickListener {
            showPopUpMenu(it)
        }
        binding.addProduct.setOnClickListener {

            val name = binding.productNameEditText.text.toString()
            val address = binding.addressEditText.text.toString()
            val cost = binding.productCostEditText.text.toString()
            val date = Calendar.getInstance().timeInMillis
            val dataProduct = EditRequest(id, cost.toFloat(), address, typeId, name, date)
            viewModel.editData(dataProduct)
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.connectionLiveData.observe(viewLifecycleOwner, connectionObserver)
        viewModel.editLiveData.observe(viewLifecycleOwner, editObserver)
    }

    private val errorMessageObserver = androidx.lifecycle.Observer<String> { showToast(it) }
    private val connectionObserver = androidx.lifecycle.Observer<String> { showToast(it) }
    private val editObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_editFragment_to_mainFragment)
    }
    private val progressObserver = Observer<Boolean> {
        binding.progress.visible(it)
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



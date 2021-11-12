package uz.gita.taskproductapi.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.taskproductapi.R
import uz.gita.taskproductapi.data.response.Product
import uz.gita.taskproductapi.databinding.FragmentMainBinding
import uz.gita.taskproductapi.presentation.adapter.ProductAdapter
import uz.gita.taskproductapi.utils.showToast
import uz.gita.taskproductapi.utils.visible
import uz.gita.taskproductapi.viewmodel.impl.PagingViewModelImpl
import uz.gita.taskproductapi.viewmodel.impl.ProductViewModelImpl

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: ProductViewModelImpl by viewModels()
    private val viewModelPaging: PagingViewModelImpl by viewModels()
    private val list = ArrayList<Product>()
    private val adapter = ProductAdapter(list)
    var currentPage = 1
    val perPage = 10
    private var progressBool = false
    private var position = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.listView.adapter = adapter
        binding.listView.layoutManager = LinearLayoutManager(requireContext())
        binding.bottomBar.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }

        adapter.setListener { product, view, pos ->
            showPopUpMenu(product, view)
            position = pos
        }

//        viewModel.getAllData()
//        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
//        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
//        viewModel.connectionLiveData.observe(viewLifecycleOwner, connectionObserver)
//        viewModel.insertLiveData.observe(viewLifecycleOwner, insertObserver)
//        viewModel.deleteLiveData.observe(viewLifecycleOwner, deleteObserver)
//        viewModel.allDataLiveData.observe(viewLifecycleOwner, allDataLiveObserver)
        viewModelPaging.allDataLiveData.observe(viewLifecycleOwner, pagingAllDataObserve)
        viewModelPaging.progressLiveData.observe(viewLifecycleOwner, progressPageObserver)
        viewModelPaging.connectionLiveData.observe(viewLifecycleOwner, connectionPagingObserver)

        binding.listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (binding.listView.canScrollVertically(1)) {
                    currentPage++
                    pages()
                }
            }

        })
        pages()
    }

    private val connectionPagingObserver = Observer<Boolean> { showToast("No Internet connection") }
    private fun pages() {
        viewModelPaging.getAllPaging(currentPage, perPage)
    }
    private val progressPageObserver = Observer<Boolean> { progressBool = it }
    private val pagingAllDataObserve = Observer<List<Product>> {
        binding.progress.visibility = if (progressBool) View.VISIBLE else View.GONE
        val oldSize = list.size
//        list.clear()
        list.addAll(it)
        adapter.notifyItemRangeInserted(oldSize, list.size)
        adapter.notifyDataSetChanged()
    }

    private val allDataLiveObserver = Observer<List<Product>> {
        list.clear()
        list.addAll(it)
        adapter.notifyDataSetChanged()
    }

    private val errorMessageObserver = Observer<String> { showToast(it) }
    private val connectionObserver = Observer<String> { showToast(it) }
    private val insertObserver = Observer<Unit> {
    }

    private val deleteObserver = Observer<Unit> {
        viewModel.getAllData()
    }
    private val progressObserver = Observer<Boolean> {
        binding.progress.visible(it)
    }

    private fun showPopUpMenu(data: Product, view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.popupmenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit -> {
                    val bundle = Bundle()
                    bundle.putSerializable("data", data)
                    adapter.notifyDataSetChanged()
                    findNavController().navigate(R.id.action_mainFragment_to_editFragment, bundle)
                }
                R.id.delete -> {
                    viewModel.delete(data.id!!)
                    adapter.notifyItemRemoved(position)
                }
            }
            true
        }
        popupMenu.show()
    }
}


/**
 *
private val allDataLiveObserver = Observer<Response<List<Product>>> { response ->
if (response.isSuccessful) {
list.clear()
response.body().let { data ->
list.addAll(data!!)
adapter.notifyDataSetChanged()
}
} else {
Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
}
}
private val insertDataObserver = Observer<Response<Unit>> { response ->
if (response.isSuccessful) {
val bundle = arguments
bundle?.let {
val data = bundle.getSerializable("insertData") as InsertRequest
viewModel.insertData(data)
adapter.notifyDataSetChanged()
}
}
}
private val deleteDataObserver = Observer<Response<Unit>> {

if (it.isSuccessful) {
viewModel.getAllData()
}
}
private val editDataObserver = Observer<Response<Unit>> {
if (it.isSuccessful) {
val bundle = arguments
bundle?.let { bundle ->
val data = bundle.getSerializable("editData") as EditRequest
viewModel.editData(data)
adapter.notifyDataSetChanged()
}
}
}

private fun showPopUpMenu(data: Product, view: View) {
val popupMenu = PopupMenu(requireContext(), view)
popupMenu.menuInflater.inflate(R.menu.popupmenu, popupMenu.menu)
popupMenu.setOnMenuItemClickListener {
when (it.itemId) {
R.id.edit -> {
val bundle = Bundle()
bundle.putSerializable("data", data)
findNavController().navigate(R.id.action_mainFragment_to_editFragment, bundle)
}
R.id.delete -> {
viewModel.deleteData(data.id!!)
adapter.notifyDataSetChanged()
}
}
true
}
popupMenu.show()
}

 */


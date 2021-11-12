package uz.gita.taskproductapi.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.taskproductapi.R
import uz.gita.taskproductapi.databinding.FragmentSplashBinding
import uz.gita.taskproductapi.viewmodel.impl.SplashViewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel by viewModels<SplashViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openMainFragmentViewModel.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        })

    }
}
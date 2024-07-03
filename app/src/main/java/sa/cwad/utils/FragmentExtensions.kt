package sa.cwad.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import sa.cwad.R

fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}
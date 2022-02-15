package com.example.meteoauthentication.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.UserPreferences
import com.example.meteoauthentication.model.GetUserStationResponse
import com.example.meteoauthentication.ui.auth.AuthActivity
import com.example.meteoauthentication.ui.startNewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    @Inject
    lateinit var userPreferences: UserPreferences

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView
                >(R.id.navbar) // todo(changed name incorrectly)
        val navController = findNavController(R.id.homeFragmentsContainer)
        bottomNavigationView.setupWithNavController(navController)

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.homeFragmentsContainer, fragment)
            commit()
        }

    fun performLogout() = lifecycleScope.launch {
      //  viewModel.logout() todo(I don't have any logout api)
        userPreferences.clear()
        startNewActivity(AuthActivity::class.java)
    }

}
package uz.example.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import uz.example.movie.databinding.ActivityMainBinding
import uz.example.movie.utils.onNavDestinationSelected

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.apply {
            bottomNavigation.addBubbleListener{id->
                onNavDestinationSelected(id,navController)
            }
            navController.addOnDestinationChangedListener { _, destination, _ ->
                bottomNavigation.setSelectedWithId(destination.id, false)
            }
        }
        /*
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navView.setupWithNavController(navController)*/
    }
}
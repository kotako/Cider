package info.kotlin.kotako.cider.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.MainActivityContract
import info.kotlin.kotako.cider.databinding.ActivityMainBinding
import info.kotlin.kotako.cider.viewmodel.MainViewModel

class MainActivity: AppCompatActivity() , MainActivityContract{

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = MainViewModel(this)

        setUpView()
    }

    private fun setUpView(){
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        val navigation = findViewById(R.id.drawer) as DrawerLayout
        toolbar.title = getString(R.string.app_name)
        toolbar.setNavigationIcon(R.mipmap.menu_white)
        toolbar.setNavigationOnClickListener { navigation.openDrawer(GravityCompat.START) }
    }

//  ----implements MainActivityContract----
//  viewModelから実行され、viewの変更を行う
    override fun startPostActivity() {
        Toast.makeText(this, "start Post", Toast.LENGTH_SHORT).show()
        PostActivity.start(this)
    }
}

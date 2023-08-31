package com.example.dailypad

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var materialToolbar: MaterialToolbar
private lateinit var drawerNavigationView: NavigationView
private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val addnote=findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val recycler=findViewById<RecyclerView>(R.id.dailynote)
        materialToolbar=findViewById(R.id.toolbar)
        drawerLayout=findViewById(R.id.drawer_layout)
        val drawerNavigationView = findViewById<NavigationView>(R.id.navigation_view)
        val headerView = drawerNavigationView.getHeaderView(0)
        val drawerTitle = headerView.findViewById<TextView>(R.id.drawer_nav_name)

       // drawerNavigationView=findViewById(R.id.navigation_view)
        val todoViewModel= ViewModelProvider(this).get(todoViewModel::class.java)
firebaseAuth= FirebaseAuth.getInstance()
setSupportActionBar(materialToolbar)
        val userEmail = firebaseAuth.currentUser?.email
        val username = userEmail?.substringBefore('@')
supportActionBar?.apply {
    subtitle=username
    setDisplayHomeAsUpEnabled(true)
}
        drawerTitle.text = username

        drawerNavigationView.setNavigationItemSelectedListener{menu->
            when(menu.itemId){
                R.id.logout->{
                    firebaseAuth.signOut()
                    startActivity(Intent(this,Login_::class.java))
                    finishAffinity()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START )
            true
        }

        val adapter=toDoAdapter(
    clickListener = { clickedNote ->
        val intent = Intent(this, add_todayNote::class.java)
        intent.putExtra("clickedNoteTitle", clickedNote.title)
        intent.putExtra("clickedNoteDesc", clickedNote.desc)
        intent.putExtra("id",clickedNote.id)
        intent.putExtra("isUpdate",true)
        startActivity(intent)
    },
    deleteListener = { deletedNote ->
        todoViewModel.delete(deletedNote)
    }
)
        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(this)

        todoViewModel.getalltodo().observe(this){todos->
            adapter.submitList(todos)

        }
        addnote.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,add_todayNote::class.java))
        })

        materialToolbar.setNavigationOnClickListener(View.OnClickListener {
           drawerLayout.openDrawer(GravityCompat.START)
        })


    }
}

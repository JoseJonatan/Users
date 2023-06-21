package com.f8fit.userssp

import android.content.Context
import android.content.DialogInterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.f8fit.userssp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)

        val isFirstTime = preferences.getBoolean(getString(R.string.sp_first_time), true)
        Log.i("SP", "${getString(R.string.sp_first_time)} = $isFirstTime")
        //Log.i("SP", "${getString(R.string.sp_username)} = ${preferences.getString(getString(R.string.sp_username), "NA")}")

        if (isFirstTime) {
            val dialogoView = layoutInflater.inflate(R.layout.dialog_register, null)

            /*MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogoView)
                .setCancelable(false)
                . setPositiveButton(R.string.dialog_confirm) { _, _ ->
                    val userName = dialogoView.findViewById<TextInputEditText>(R.id.etUsername)
                        .text.toString()
                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), userName)
                            .apply()
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                }
                .show()*/
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogoView)
                .setCancelable(false)
                . setPositiveButton(R.string.dialog_confirm) { _, _ -> }
                .create()

            dialog.show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
                val userName = dialogoView.findViewById<TextInputEditText>(R.id.etUsername)
                    .text.toString()
                if (userName.isBlank()){
                    Toast.makeText(this, R.string.register_invalid, Toast.LENGTH_SHORT).show()
                } else {
                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), userName)
                            .apply()
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        } else {
            val username = preferences.getString(getString(R.string.sp_username), getString(R.string.sp_first_time))
            Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()
        }

        userAdapter = UserAdapter(getUser(), this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply {

            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }

        val swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userAdapter.remove(viewHolder.adapterPosition)
            }
        })
        swipeHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun getUser(): MutableList<User>{
        val users = mutableListOf<User>()

        val jon = User(1, "Jonatan", "Islas", "https://frogames.es/wp-content/uploads/2020/09/alain-1.jpg")
        val adri = User(2, "Adriana", "Ruiz", "https://upload.wikimedia.org/wikipedia/commons/b/b2/Samanta_villar.jpg")
        val peter = User(3,"Pedro", "Quezada", "https://live.staticflickr.com/974/42098804942_b9ce35b1c8_b.jpg")

        users.add(jon)
        users.add(adri)
        users.add(peter)
        users.add(jon)
        users.add(adri)
        users.add(peter)
        users.add(jon)
        users.add(adri)
        users.add(peter)
        users.add(jon)
        users.add(adri)
        users.add(peter)
        users.add(jon)
        users.add(adri)
        users.add(peter)
        users.add(jon)
        users.add(adri)
        users.add(peter)
        users.add(jon)
        users.add(adri)
        users.add(peter)

        return users
    }

    override fun onClick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFulNaame()}", Toast.LENGTH_SHORT).show()
    }
}
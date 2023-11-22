package com.example.cityquest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cityquest.R
import com.example.cityquest.adapters.UserAdapter
import com.example.cityquest.databinding.ActivityChatListBinding
import com.example.cityquest.items.User
import com.parse.ParseQuery
import com.parse.ParseUser

class ChatListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatListBinding
    private lateinit var userAdapter: UserAdapter
    private var userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListView()
        loadUsersFromParse()
    }

    private fun setupListView() {
        userAdapter = UserAdapter(this, userList)
        binding.listView.adapter = userAdapter
    }

    private fun loadUsersFromParse() {
        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
        query.findInBackground { users, e ->
            if (e == null) {
                userList.clear()
                users?.forEach { parseUser ->
                    val user = User(
                        parseUser.get("name") as? String,
                        parseUser.objectId,
                        parseUser.username
                    )
                    if (parseUser.get("name") as? String != null)
                        userList.add(user)
                }
                userAdapter.notifyDataSetChanged()
            } else {
                val og = null
                Log.e("ChatListActivity", "Error al cargar usuarios: ${e.localizedMessage}")
                Toast.makeText(this, "Error al cargar usuarios: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
package com.tatvum.realtimechat.model.user

import com.google.firebase.firestore.FirebaseFirestore
import com.tatvum.realtimechat.model.Model
import com.tatvum.realtimechat.model.user.listeners.AddUser
import com.tatvum.realtimechat.model.user.listeners.CheckUser
import com.tatvum.realtimechat.model.user.listeners.GetAllUsers
import com.tatvum.realtimechat.model.user.listeners.GetUser
import timber.log.Timber


const val USER_COLLECTION: String = "users"
const val USER_NAME_FIELD = "userName"

class UserModel {
    private var database: FirebaseFirestore = Model.getInstance()

    fun checkUser(userName: String, checkUserListen: CheckUser) {
        val userList = mutableListOf<User>()
        database.collection(USER_COLLECTION)
            .whereEqualTo(USER_NAME_FIELD, userName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val user: User = document.toObject(User::class.java)
                    userList.add(user)
                }
                if (userList.size != 0) {
                    checkUserListen.userFound(true)
                } else {
                    checkUserListen.userFound(false)
                }
            }
            .addOnFailureListener {
                checkUserListen.userFound(false)
            }
    }

    fun addUser(
        firstName: String,
        lastName: String,
        userName: String,
        addUser: AddUser
    ) {
        val user = User(userName, firstName, lastName, "", null)
        database.collection(USER_COLLECTION).add(user)
            .addOnSuccessListener { documentReference ->
                Timber.i("DocumentSnapshot written with ID: ${documentReference.id}")
                addUser.userAdded(true)
            }
            .addOnFailureListener { exception ->
                Timber.i(exception.toString())
                addUser.userAdded(false)
            }
    }


    fun getUserList(getAllUsers: GetAllUsers) {
        val userList = mutableListOf<User>()
        database.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val user: User = document.toObject(User::class.java)
                    userList.add(user)
                }
                getAllUsers.getUsers(userList)
            }
            .addOnFailureListener { exception ->
                Timber.i(exception.toString())
                getAllUsers.getUsers(null)
            }
    }

    fun getUser(userName: String, getUser: GetUser) {
        val userList = mutableListOf<User>()
        database.collection("users")
            .whereEqualTo(USER_NAME_FIELD, userName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val user: User = document.toObject(User::class.java)
                    userList.add(user)
                }
                getUser.getUser(userList[0])
            }
            .addOnFailureListener { exception ->
                Timber.i(exception.toString())
                getUser.getUser(null)
            }
    }

}
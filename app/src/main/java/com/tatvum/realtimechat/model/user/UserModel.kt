package com.tatvum.realtimechat.model.user

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.tatvum.realtimechat.model.Model
import com.tatvum.realtimechat.model.user.listeners.*
import timber.log.Timber


const val USER_COLLECTION: String = "users"
const val USER_NAME_FIELD = "userName"
const val USERS_FIELD = "users"


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
            .addOnFailureListener { checkUserListen.userFound(false) }
    }

    fun addUser(firstName: String, lastName: String, userName: String, addUser: AddUser) {
        val user = User(userName, firstName, lastName, "", null)
        database.collection(USER_COLLECTION).add(user)
            .addOnSuccessListener { documentReference ->
                Timber.i("DocumentSnapshot written with ID: ${documentReference.id}")
                addUser.userAdded(true)
            }
            .addOnFailureListener { addUser.userAdded(false) }
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
            .addOnFailureListener { getAllUsers.getUsers(null) }
    }

    fun observeUser(userName: String, realTimeUser: RealTimeUser) {
        database.collection("users")
            .whereEqualTo(USER_NAME_FIELD, userName)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    realTimeUser.userUpdated(false)
                } else {
                    if (snapshot != null) {
                        realTimeUser.userUpdated(true)
                    }
                }
            }
    }


    fun getUser(userName: String, getUser: GetUser) {
        val userList = mutableListOf<User>()
        database.collection("users")
            .whereEqualTo(USER_NAME_FIELD, userName)
            .get()
            .addOnSuccessListener { documents ->
                var id = ""
                for (document in documents) {
                    val user: User = document.toObject(User::class.java)
                    userList.add(user)
                    id = document.id
                    break
                }
                getUser.getUser(userList[0], id)
            }
            .addOnFailureListener { getUser.getUser(null, "") }
    }

    fun updateUser(username: String, toValue: String, updateUser: UpdateUser) {
        getUser(username, GetUserForUpdate(toValue, updateUser))
    }

    inner class GetUserForUpdate(
        private val toValue: String,
        private val updateUser: UpdateUser
    ) : GetUser {
        override fun getUser(user: User?, id: String) {
            if (user != null) {
                val userList = user.users
                val userRef = database.collection(USER_COLLECTION).document(id)
                if (userList == null) {
                    val tempList = listOf(toValue)
                    userRef.update(USERS_FIELD, tempList)
                        .addOnSuccessListener {
                            updateUser.userUpdated(true)
                        }
                        .addOnFailureListener {
                            updateUser.userUpdated(false)
                        }
                } else {
                    if (!userList.contains(toValue)) {
                        userRef.update(USERS_FIELD, FieldValue.arrayUnion(toValue))
                            .addOnSuccessListener { updateUser.userUpdated(true) }
                            .addOnFailureListener { updateUser.userUpdated(false) }
                    } else {
                        updateUser.userUpdated(true)
                    }
                }
            }
        }
    }
}

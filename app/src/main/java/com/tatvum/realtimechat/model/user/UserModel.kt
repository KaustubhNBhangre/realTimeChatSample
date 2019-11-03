package com.tatvum.realtimechat.model.user

import com.google.firebase.firestore.FirebaseFirestore
import com.tatvum.realtimechat.model.Model
import com.tatvum.realtimechat.model.user.interfaces.AddUserListener
import com.tatvum.realtimechat.model.user.interfaces.CheckUserListener
import timber.log.Timber


const val USER_COLLECTION: String = "users"
const val USER_NAME_FIELD = "userName"

class UserModel {
    private var database: FirebaseFirestore = Model.getInstance()

    fun checkUser(userName: String, checkUserListen: CheckUserListener) {
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
                }else{
                    checkUserListen.userFound(false)
                }
            }
            .addOnFailureListener {
                checkUserListen.userFound(false)
            }
    }

    fun addUser(
        userName: String,
        firstName: String,
        lastName: String,
        addUserListener: AddUserListener
    ) {
        checkUser(userName, UserCheck(userName, firstName, lastName, database, addUserListener))
    }


    fun getUserList(): List<User> {
        val userList = mutableListOf<User>()
        database.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val user: User = document.toObject(User::class.java)
                    userList.add(user)
                }
            }
            .addOnFailureListener { exception ->
                Timber.i(exception.toString())
            }
        return userList
    }
}
class UserCheck constructor(
    private val userName: String,
    private val firstName: String,
    private val lastName: String,
    private val database: FirebaseFirestore,
    private val addUserListener: AddUserListener
) : CheckUserListener {
    override fun userFound(status: Boolean) {
        if (status) {
            addUserListener.userAdded(User())
        } else {
            val user = User(userName, firstName, lastName, "", null)
            database.collection(USER_COLLECTION).add(user)
                .addOnSuccessListener { documentReference ->
                    Timber.i("DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { exception ->
                    Timber.i(exception.toString())
                }
        }
    }
}
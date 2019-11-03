package com.tatvum.realtimechat.model

import com.google.firebase.firestore.FirebaseFirestore

class Model {
    companion object {
        fun getInstance(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }
    }
}
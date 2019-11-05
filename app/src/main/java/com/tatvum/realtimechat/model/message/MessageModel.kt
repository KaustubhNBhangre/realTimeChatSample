package com.tatvum.realtimechat.model.message

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.tatvum.realtimechat.model.Model
import com.tatvum.realtimechat.model.message.listeners.AddMessage
import com.tatvum.realtimechat.model.message.listeners.GetAllMessages
import com.tatvum.realtimechat.model.message.listeners.GetMessage
import com.tatvum.realtimechat.model.message.listeners.RealtimeMessage
import timber.log.Timber

const val TIME_STAMP_FIELD = "timeStamp"
const val QUERY_LIMIT: Long = 100

class MessageModel {
    private var database: FirebaseFirestore = Model.getInstance()

    private lateinit var regListener:ListenerRegistration;
    private fun createMessage(
        from: String,
        to: String,
        message: String,
        imageUrl: String = ""
    ): Message {
        return Message(from, to, message, imageUrl, Timestamp.now())
    }

    private fun getCollectionName(from: String, to: String): String {
        val list = mutableListOf(from, to)
        list.sort()
        return list[0] + list[1]
    }

    fun addMessage(from: String, to: String, message: String, addMessage: AddMessage) {
        val msgObj = createMessage(from, to, message)
        val collectionName = getCollectionName(from, to)
        database.collection(collectionName).add(msgObj)
            .addOnSuccessListener { documentReference ->
                Timber.i("DocumentSnapshot written with ID: ${documentReference.id}")
                addMessage.messageAdded(true)
            }
            .addOnFailureListener { exception ->
                Timber.i(exception.toString())
                addMessage.messageAdded(false)
            }
    }

    fun getMessageList(from: String, to: String, getAllMessages: GetAllMessages) {
        val messageList = mutableListOf<Message>()
        val collectionName = getCollectionName(from, to)
        database.collection(collectionName).orderBy(TIME_STAMP_FIELD, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val message: Message = document.toObject(Message::class.java)
                    messageList.add(message)
                }
                getAllMessages.getMessages(messageList)
            }
            .addOnFailureListener { getAllMessages.getMessages(null) }
    }

    fun getMessageRealtime(from: String, to: String, getAllMessages: GetAllMessages) {
        val messageList = mutableListOf<Message>()
        val collectionName = getCollectionName(from, to)
        database.collection(collectionName).orderBy(TIME_STAMP_FIELD, Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    getAllMessages.getMessages(null)
                } else {
                    if (snapshot != null) {
                        for (documentChange in snapshot.documentChanges) {
                            val message: Message =
                                documentChange.document.toObject(Message::class.java)
                            messageList.add(message)
                        }
                        getAllMessages.getMessages(messageList)
                    }
                }
            }
    }

    fun observeMessageTable(from: String, to: String, realtimeMessage: RealtimeMessage) {
        val collectionName = getCollectionName(from, to)
        val query =
            database.collection(collectionName).orderBy(TIME_STAMP_FIELD, Query.Direction.ASCENDING)
        regListener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                realtimeMessage.messageUpdated(false)
            } else {
                if (snapshot != null) {
                    for (documentChange in snapshot.documentChanges) {
                        if (documentChange.type == DocumentChange.Type.ADDED)
                            realtimeMessage.messageUpdated(true)
                    }
                }
            }
        }
    }

    fun removeRegListener(){
       regListener.remove()
    }

    fun getLastMessage(from: String, to: String, getMessage: GetMessage) {
        var message: Message? = null
        val collectionName = getCollectionName(from, to)
        database.collection(collectionName).orderBy(TIME_STAMP_FIELD, Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    message = document.toObject(Message::class.java)
                    break
                }
                getMessage.getMessage(message)
            }
            .addOnFailureListener { exception ->
                Timber.i(exception)
                getMessage.getMessage(null)
            }
    }
}
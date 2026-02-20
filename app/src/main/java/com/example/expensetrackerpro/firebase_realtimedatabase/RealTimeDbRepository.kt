package com.example.expensetrackerpro.firebase_realtimedatabase

import android.content.Context
import android.provider.Settings
import com.example.expensetrackerpro.firebase.ResultState
import com.google.firebase.database.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealTimeDbRepository(
    private val db: DatabaseReference,
    private val context: Context
) : RealTimeRepository {

    private fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun insert(item: RealTimeUser.RealTimeItems): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val deviceId = getDeviceId()
        val userNode = db.child("users").child(deviceId).child("items")

        userNode.push().setValue(item)
            .addOnCompleteListener {
                if (it.isSuccessful) trySend(ResultState.Success("Data inserted successfully"))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it))
            }

        awaitClose { close() }
    }

    override fun getItems(): Flow<ResultState<List<RealTimeUser>>> = callbackFlow {
        trySend(ResultState.Loading)
        val deviceId = getDeviceId()
        val userNode = db.child("users").child(deviceId).child("items")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<RealTimeUser>()
                snapshot.children.forEach { data ->
                    val item = data.getValue(RealTimeUser.RealTimeItems::class.java)
                    val key = data.key
                    if (item != null && key != null) list.add(RealTimeUser(item, key))
                }
                trySend(ResultState.Success(list))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(Exception(error.message)))
            }
        }

        userNode.addValueEventListener(listener)

        awaitClose {
            userNode.removeEventListener(listener)
            close()
        }
    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val deviceId = getDeviceId()
        val userNode = db.child("users").child(deviceId).child("items")

        userNode.child(key).removeValue()
            .addOnCompleteListener { trySend(ResultState.Success("Item deleted")) }
            .addOnFailureListener { trySend(ResultState.Error(it)) }

        awaitClose { close() }
    }

    override fun update(res: RealTimeUser): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val deviceId = getDeviceId()
        val userNode = db.child("users").child(deviceId).child("items")

        val map = hashMapOf<String, Any>(
            "userFirstName" to res.items.userFirstName,
            "email" to res.items.email,
            "password" to res.items.password
        )

        userNode.child(res.key!!).updateChildren(map)
            .addOnCompleteListener { trySend(ResultState.Success("Updated successfully")) }
            .addOnFailureListener { trySend(ResultState.Error(it)) }

        awaitClose { close() }
    }
}

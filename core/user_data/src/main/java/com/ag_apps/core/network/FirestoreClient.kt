package com.ag_apps.core.network

import com.ag_apps.core.domain.User
import com.ag_apps.core.domain.UserDataSource
import com.ag_apps.core.domain.util.DataError
import com.ag_apps.core.domain.util.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Ahmed Guedmioui
 */
class FirestoreClient(
    private val applicationScope: CoroutineScope,
) {

    private val tag = "FirestoreClient"

    private var firestore = FirebaseFirestore.getInstance()

    fun insertUser(user: User): Flow<Result<String, DataError.Network>> {
        return callbackFlow {
            firestore.collection("users")
                .add(user.toHashMap())
                .addOnSuccessListener { document ->
                    Timber.tag(tag).d("insertUser with ID: ${document.id}")
                    applicationScope.launch {
                        // Update the user in firestore with the id field
                        // because when we insert a new user, the id field is empty
                        updateUser(user.copy(id = document.id)).collect { updateResult ->
                            when(updateResult) {
                                is Result.Success -> trySend(Result.Success(document.id))
                                is Result.Error -> trySend(Result.Error(updateResult.error))
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    Timber.tag(tag).d("Error inserting user: ${e.message}")
                    trySend(Result.Error(DataError.Network.UNKNOWN))
                }

            awaitClose { close() }
        }
    }

    fun updateUser(user: User): Flow<Result<String, DataError.Network>> {
        return callbackFlow {
            firestore.collection("users")
                .document(user.id)
                .set(user.toHashMap())
                .addOnSuccessListener {
                    Timber.tag(tag).d("updateUser with ID: ${user.id}")
                    trySend(Result.Success(user.id))
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    Timber.tag(tag).d("Error updating user: ${e.message}")
                    trySend(Result.Error(DataError.Network.UNKNOWN))
                }

            awaitClose { close() }
        }
    }

    fun getUser(email: String): Flow<Result<User, DataError.Network>> {
        return callbackFlow {
            firestore.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    var user: User? = null

                    for (document in result) {
                        if (document.data["email"] == email) {
                            user = document.data.toUser()
                            println(tag + "user found: ${user.email}")
                            trySend(Result.Success(user))
                        }
                    }

                    if (user == null) {
                        println(tag + "user not found: $email")
                        trySend(Result.Error(DataError.Network.NOT_FOUND))
                    }
                }
                .addOnFailureListener { e ->
                    Timber.tag(tag).d("Error getting users collection null")
                    trySend(Result.Error(DataError.Network.UNKNOWN))
                }

            awaitClose { close() }
        }
    }

    private fun User.toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "email" to email,
            "id" to id,
            "name" to name,
        )
    }

    private fun Map<String, Any>.toUser(): User {
        return User(
            email = this["email"] as String,
            id = this["id"] as String,
            name = this["name"] as String
        )
    }
}

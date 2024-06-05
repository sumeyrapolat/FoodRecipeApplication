package com.example.foodrecipeapplicaiton.loginviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loginUser(username: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            firestore.collection("users").whereEqualTo("username", username).get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        onFailure(Exception("No user found with this username"))
                    } else {
                        val email = documents.documents[0].getString("email")
                        if (email != null) {
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        onSuccess()
                                    } else {
                                        task.exception?.let { onFailure(it) }
                                    }
                                }
                        } else {
                            onFailure(Exception("Email not found"))
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
    }
}

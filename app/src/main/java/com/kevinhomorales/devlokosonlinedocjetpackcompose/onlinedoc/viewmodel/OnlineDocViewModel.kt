package com.kevinhomorales.devlokosonlinedocjetpackcompose.onlinedoc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OnlineDocViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private var contentListener: ListenerRegistration? = null
    private var viewersListener: ListenerRegistration? = null
    private var userId: String? = null
    private var lastViewerIds = emptySet<String>()
    private var isTyping = false

    var content = MutableStateFlow("")
        private set

    var activeViewers = MutableStateFlow(0)
        private set

    var notificationMessage = MutableStateFlow<String?>(null)

    init {
        signInAndStart()
    }

    private fun signInAndStart() {
        FirebaseAuth.getInstance().signInAnonymously().addOnSuccessListener {
            userId = it.user?.uid
            startListeningContent()
            addViewer()
            observeViewers()
        }
    }

    private fun startListeningContent() {
        contentListener =
            db.collection("shared").document("editor").addSnapshotListener { snapshot, _ ->
                val newText = snapshot?.getString("content") ?: return@addSnapshotListener
                if (!isTyping && newText != content.value) {
                    content.value = newText
                }
            }
    }

    fun updateContent(newText: String) {
        isTyping = true
        content.value = newText
        db.collection("shared").document("editor").set(mapOf("content" to newText))
            .addOnSuccessListener {
                viewModelScope.launch {
                    kotlinx.coroutines.delay(300)
                    isTyping = false
                }
            }
    }

    private fun addViewer() {
        userId?.let {
            db.collection("viewers").document(it).set(
                mapOf("timestamp" to FieldValue.serverTimestamp())
            )
        }
    }

    fun removeViewer() {
        userId?.let {
            db.collection("viewers").document(it).delete()
        }
    }

    private fun observeViewers() {
        viewersListener = db.collection("viewers").addSnapshotListener { snapshot, _ ->
            val currentIds = snapshot?.documents?.map { it.id }?.toSet() ?: emptySet()
            activeViewers.value = currentIds.size

            val added = currentIds - lastViewerIds
            val removed = lastViewerIds - currentIds

            if (added.isNotEmpty()) {
                showNotification("USUARIO CONECTADO")
            } else if (removed.isNotEmpty()) {
                showNotification("USUARIO SALIÓ")
            }

            lastViewerIds = currentIds
        }
    }

    private fun showNotification(message: String) {
        viewModelScope.launch {
            notificationMessage.value = message
            kotlinx.coroutines.delay(2000)
            notificationMessage.value = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        contentListener?.remove()
        viewersListener?.remove()
        removeViewer()
    }
}

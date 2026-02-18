package com.example.expensetrackerpro.google_firebase

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.expensetrackerpro.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context
) {

    private val oneTapClient: SignInClient = Identity.getSignInClient(context)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Start Google One Tap sign-in
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            null
        }
        return result?.pendingIntent?.intentSender
    }

    // Handle sign-in result from Intent
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.let {
                    UserData(
                        userId = it.uid,
                        username = it.displayName,
                        profilePictureUrl = it.photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    // Sign out user
    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
        }
    }

    // Get current signed-in user
    fun getSignedInUser(): UserData? {
        return auth.currentUser?.let {
            UserData(
                userId = it.uid,
                username = it.displayName,
                profilePictureUrl = it.photoUrl?.toString()
            )
        }
    }

    // Build Google One Tap request
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}

package com.fggc.garden_mate.data.network

import android.content.Context
import android.util.Log
import androidx.navigation.compose.rememberNavController
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Sensor
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.HubEvent
import com.fggc.garden_mate.domain.model.UserData


object Backend {

    private const val TAG = "Backend"

    fun initialize(applicationContext: Context) : Backend {
        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Log.i(TAG, "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e(TAG, "Could not initialize Amplify", e)
        }
        // in Backend.initialize() function, after the try/catch block but before the return statement

        Log.i(TAG, "registering hub event")

// listen to auth event
        Amplify.Hub.subscribe(HubChannel.AUTH) { hubEvent: HubEvent<*> ->

            when (hubEvent.name) {
                InitializationStatus.SUCCEEDED.toString() -> {
                    Log.i(TAG, "Amplify successfully initialized")
                }
                InitializationStatus.FAILED.toString() -> {
                    Log.i(TAG, "Amplify initialization failed")
                }
                else -> {
                    when (AuthChannelEventName.valueOf(hubEvent.name)) {
                        AuthChannelEventName.SIGNED_IN -> {
                            updateUserData(true)
                            Log.i(TAG, "HUB : SIGNED_IN")

                        }
                        AuthChannelEventName.SIGNED_OUT -> {
                            updateUserData(false)
                            Log.i(TAG, "HUB : SIGNED_OUT")
                        }
                        else -> Log.i(TAG, """HUB EVENT:${hubEvent.name}""")
                    }
                }
            }
        }

        Log.i(TAG, "retrieving session status")

// is user already authenticated (from a previous execution) ?
        Amplify.Auth.fetchAuthSession(
            { result ->
                Log.i(TAG, result.toString())
                val cognitoAuthSession = result as AWSCognitoAuthSession
                // update UI
                this.updateUserData(cognitoAuthSession.isSignedIn)
                when (cognitoAuthSession.identityIdResult.type) {
                    AuthSessionResult.Type.SUCCESS ->  Log.i(TAG, "IdentityId: " + cognitoAuthSession.identityIdResult.value)
                    AuthSessionResult.Type.FAILURE -> Log.i(TAG, "IdentityId not present because: " + cognitoAuthSession.identityIdResult.error.toString())
                }
            },
            { error -> Log.i(TAG, error.toString()) }
        )
        return this
    }

    private fun updateUserData(withSignedInStatus : Boolean) {
        UserData.setSignedIn(withSignedInStatus)
    }

    fun signUp(userData: UserData) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), userData.email)
            .build()
        Amplify.Auth.signUp(userData.username, userData.password, options,
            { Log.i("AuthQuickStart", "Sign up succeeded: $it") },
            { Log.e ("AuthQuickStart", "Sign up failed", it) }
        )
    }

    fun confirmSignUp(userData: UserData, code: String){
        Amplify.Auth.confirmSignUp(
            userData.username, code,
            { result ->
                if (result.isSignUpComplete) {
                    Log.i("AuthQuickstart", "Confirm signUp succeeded")
                } else {
                    Log.i("AuthQuickstart","Confirm sign up not complete")
                }
            },
            { Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
        )
    }

    fun signOut() {
        Log.i(TAG, "Initiate Signout Sequence")

        Amplify.Auth.signOut { signOutResult ->
            when(signOutResult) {
                is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
                    // Sign Out completed fully and without errors.
                    Log.i("AuthQuickStart", "Signed out successfully")
                }
                is AWSCognitoAuthSignOutResult.PartialSignOut -> {
                    // Sign Out completed with some errors. User is signed out of the device.
                    signOutResult.hostedUIError?.let {
                        Log.e("AuthQuickStart", "HostedUI Error", it.exception)
                        // Optional: Re-launch it.url in a Custom tab to clear Cognito web session.

                    }
                    signOutResult.globalSignOutError?.let {
                        Log.e("AuthQuickStart", "GlobalSignOut Error", it.exception)
                        // Optional: Use escape hatch to retry revocation of it.accessToken.
                    }
                    signOutResult.revokeTokenError?.let {
                        Log.e("AuthQuickStart", "RevokeToken Error", it.exception)
                        // Optional: Use escape hatch to retry revocation of it.refreshToken.
                    }
                }
                is AWSCognitoAuthSignOutResult.FailedSignOut -> {
                    // Sign Out failed with an exception, leaving the user signed in.
                    Log.e("AuthQuickStart", "Sign out Failed", signOutResult.exception)
                }
            }
        }
    }

    fun signIn(userData: UserData) {
        Log.i(TAG, "Initiate Signin Sequence")

        Amplify.Auth.signIn(userData.username, userData.password,
            { result ->
                if (result.isSignedIn) {
                    Log.i("AuthQuickstart", "Sign in succeeded")
                } else {
                    Log.i("AuthQuickstart", "Sign in not complete")
                }
            },
            { Log.e("AuthQuickstart", "Failed to sign in", it) }
        )
    }

//    fun createTodo(){
//        val item = Sensor.builder()
//            .name("Build Android application")
//            .priority(Priority.NORMAL)
//            .build()
//
//        Amplify.DataStore.save(item,
//            { Log.i("Tutorial", "Saved item: ${item.name}") },
//            { Log.e("Tutorial", "Could not save item to DataStore", it) }
//        )
//    }

//    fun createTodo2(){
//        val date = Date()
//        val offsetMillis = TimeZone.getDefault().getOffset(date.time).toLong()
//        val offsetSeconds = TimeUnit.MILLISECONDS.toSeconds(offsetMillis).toInt()
//        val temporalDateTime = Temporal.DateTime(date, offsetSeconds)
//        val item = Todo.builder()
//            .name("Finish quarterly taxes")
//            .priority(Priority.HIGH)
//            .completedAt(temporalDateTime)
//            .build()
//
//        Amplify.DataStore.save(item,
//            { Log.i("Tutorial", "Saved item: ${item.name}") },
//            { Log.e("Tutorial", "Could not save item to DataStore", it) }
//        )
//    }

    fun queryTodos() {
        Amplify.DataStore.query(Sensor::class.java,
            { sensors ->
                while (sensors.hasNext()) {
                    val sensor: Sensor = sensors.next()
                    Log.i("DataStore", "==== Sensor ====")
                    Log.i("DataStore", "Name: ${sensor.id}")
                    sensor.humidity?.let { todoPriority -> Log.i("Tutorial", "Priority: $todoPriority") }
                    sensor.temperature?.let { todoCompletedAt -> Log.i("Tutorial", "CompletedAt: $todoCompletedAt") }
                }
            },
            { Log.e("Tutorial", "Could not query DataStore", it)  }
        )
    }

//    fun updateTodos(){
//        Amplify.DataStore.query(Todo::class.java, Where.matches(Todo.NAME.eq("Finish quarterly taxes")),
//            { matches ->
//                if (matches.hasNext()) {
//                    val todo = matches.next()
//                    val updatedTodo = todo.copyOfBuilder()
//                        .name("File quarterly taxes")
//                        .build()
//                    Amplify.DataStore.save(updatedTodo,
//                        { Log.i("Tutorial", "Updated item: ${updatedTodo.name}") },
//                        { Log.e("Tutorial", "Update failed.", it) }
//                    )
//                }
//            },
//            { Log.e("Tutorial", "Query failed", it) }
//        )
//    }
//
//    fun deleteTodo(){
//        Amplify.DataStore.query(Todo::class.java, Where.matches(Todo.NAME.eq("File quarterly taxes")),
//            { matches ->
//                if (matches.hasNext()) {
//                    val toDeleteTodo = matches.next()
//                    Amplify.DataStore.delete(toDeleteTodo,
//                        { Log.i("Tutorial", "Deleted item: ${toDeleteTodo.name}") },
//                        { Log.e("Tutorial", "Delete failed.", it) }
//                    )
//                }
//            },
//            { Log.e("Tutorial", "Query failed.", it) }
//        )
//    }

}
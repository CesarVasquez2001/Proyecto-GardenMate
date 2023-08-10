package com.fggc.garden_mate.data.network

import android.content.Context
import android.util.Log
import com.amplifyframework.api.ApiCategory
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.api.graphql.GraphQLRequest
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignOutOptions
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.MensajeComando
import com.amplifyframework.datastore.generated.model.Sensor
import com.fggc.garden_mate.domain.model.LoginState
import com.fggc.garden_mate.domain.model.SignUpState
import com.fggc.garden_mate.domain.model.VerificationCodeState

interface AmplifyService {

    fun configureAmplify(context: Context)

    fun signUp(state: SignUpState, onComplete: () -> Unit)

    fun verifyCode(state: VerificationCodeState, onComplete: () -> Unit)

    fun login(state: LoginState, onComplete: () -> Unit)

    fun logOut(onComplete: () -> Unit)

    fun queryTodos()

    fun sendComando(string: String)
}
class AmplifyServiceImpl : AmplifyService {
    override fun configureAmplify(context: Context) {
        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(context)
            Log.i("AWS CONFIGURE", "Initialized Amplify")
        } catch (e: Exception) {
            Log.e("AWS CONFIGURE", "Amplify configuration failed", e)
        }
    }

    override fun signUp(state: SignUpState, onComplete: () -> Unit) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), state.email)
            .build()

        Amplify.Auth.signUp(state.username, state.password, options,
            { onComplete() },
            { Log.e("KILO", "Sign Up Error:", it) }
        )
    }

    override fun verifyCode(state: VerificationCodeState, onComplete: () -> Unit) {
        Amplify.Auth.confirmSignUp(
            state.username,
            state.code,
            { onComplete() },
            { Log.e("KILO", "Verification Failed: ", it) }
        )
    }

    override fun login(state: LoginState, onComplete: () -> Unit) {
        Amplify.Auth.signIn(
            state.username,
            state.password,
            { onComplete() },
            { Log.e("KILO", "Login Error:", it) }
        )
    }

    override fun logOut(onComplete: () -> Unit) {
        val options = AuthSignOutOptions.builder()
            .globalSignOut(true)
            .build()

        Amplify.Auth.signOut(options){
            onComplete()
            Log.e("KILO", "Sign Out Failed: ")
        }
    }

    override fun queryTodos() {
        Amplify.DataStore.query(
            Sensor::class.java,
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

    override fun sendComando(id: String) {
        Amplify.DataStore.query(MensajeComando::class.java, Where.identifier(MensajeComando::class.java, id),
            { matches ->
                if (matches.hasNext()) {
                    val original = matches.next()
                    val edited = original.copyOfBuilder()
                        .estado(true)
                        .build()
                    Amplify.DataStore.save(edited,
                        { Log.i("MyAmplifyApp", "Updated a post") },
                        { Log.e("MyAmplifyApp", "Update failed", it) }
                    )
                }
            },
            { Log.e("MyAmplifyApp", "Query failed", it) }
        )
    }
}
package com.fggc.garden_mate.presentation.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.model.temporal.Temporal
/*import com.amplifyframework.datastore.generated.model.Priority
import com.amplifyframework.datastore.generated.model.Todo*/
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SensorViewModel (
) : ViewModel() {

    var openDialog by mutableStateOf(false)



    fun closeDialog() {
        openDialog = false
    }

    fun openDialog() {
        openDialog = true
    }
}
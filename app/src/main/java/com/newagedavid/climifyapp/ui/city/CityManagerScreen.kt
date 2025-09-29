package com.newagedavid.climifyapp.ui.city

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newagedavid.climifyapp.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityManagerScreen(
    navController: NavController,
    cityManagerViewModel: CityManagerViewModel = getViewModel()
) {
    val context = LocalContext.current

    var isEditing by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // State for dialog input
    var newCityName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val cityWeatherList by cityManagerViewModel.citiesWithWeather.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("City Manager") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (cityWeatherList == null || cityWeatherList!!.isEmpty() ) {
                            Toast.makeText(context, "You need to add your favourite cities!", Toast.LENGTH_LONG).show()

                        }else {
                            navController.navigate("home") {
                                // Pop up to the start destination so you don't stack multiple screens
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            if (cityWeatherList == null || cityWeatherList!!.isEmpty() ) {
                                Toast.makeText(context, "There is no city to edit. Kindly add your favourite city!", Toast.LENGTH_LONG).show()

                            }else {
                                isEditing = !isEditing
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        Text("Edit", style = MaterialTheme.typography.bodySmall)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { showAddDialog = true }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        Text("Add", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (cityWeatherList == null || cityWeatherList!!.isEmpty() ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationCity,
                                contentDescription = "No cities",
                                modifier = Modifier.size(64.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No cities added yet",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tap the + button to add your first city",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(cityWeatherList!!) { cityWeather ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 🔹 Show minus icon only in editing mode
                            if (isEditing) {
                                Icon(
                                    imageVector = Icons.Default.RemoveCircle, // filled minus icon
                                    contentDescription = "Delete city",
                                    tint = Color.Red,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .fillMaxHeight()
                                        .padding(end = 16.dp)
                                        .clickable {
                                            // cityManagerViewModel.deleteCity(cityWeather.name) // call your delete function

                                            cityManagerViewModel.deleteCity(cityWeather.name) { success, error ->
                                                if (!success) {
                                                    Toast.makeText(context, error ?: "Failed to delete ${cityWeather.name}. Try again later.", Toast.LENGTH_LONG).show()
                                                }
                                            }

                                        }
                                )
                            }

                            Column {

                                Text(cityWeather.name, style = MaterialTheme.typography.titleMedium)
                                cityWeather.description?.let {
                                    Text(it, style = MaterialTheme.typography.bodySmall)
                                }
                            }

                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${cityWeather.temp ?: "--"}°C",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.width(8.dp))
                            VerticalDivider(modifier = Modifier.height(18.dp), 1.dp)
                            Spacer(Modifier.width(8.dp))
                            cityWeather.icon?.let {
                                Icon(
                                    painter = painterResource(id = mapWeatherIcon(cityWeather.description, cityWeather.icon)),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }


    // Add City Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { if (!isLoading) showAddDialog = false },
            title = { Text("Add City") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newCityName,
                        onValueChange = { newCityName = it },
                        label = { Text("City name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (errorMessage != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            confirmButton = {
                TextButton (
                    onClick = {
                        if (newCityName.isNotBlank()) {
                            isLoading = true
                            errorMessage = null
                            cityManagerViewModel.addCity(newCityName) { success, error ->
                                isLoading = false
                                if (success) {
                                    showAddDialog = false
                                    newCityName = ""
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("City added successfully")
                                    }
                                } else {
                                    errorMessage = error ?: "Unknown error"
                                }
                            }
                        }
                    },

                    enabled = !isLoading && newCityName.isNotBlank()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(18.dp)
                        )
                    } else {
                        Text("Add")
                    }
                }
            },
            dismissButton = {
                TextButton (onClick = { if (!isLoading) showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}}

fun mapWeatherIcon(description: String?, iconCode: String?): Int {
    val isNight = iconCode?.endsWith("n") == true

    if(description == null){
        return R.drawable.ic_sunny
    }

    return when {
        description.contains("clear", ignoreCase = true) -> {
            if (isNight) R.drawable.ic_clear_moon_stars else R.drawable.ic_sunny
        }
        description.contains("cloud", ignoreCase = true) -> {
            if (isNight) R.drawable.ic_partly_cloudy_night else R.drawable.ic_partly_cloudy_day
        }
        description.contains("rain", ignoreCase = true) -> R.drawable.ic_rain
        description.contains("thunder", ignoreCase = true) -> R.drawable.ic_thunder
        description.contains("snow", ignoreCase = true) -> R.drawable.ic_snowy
        description.contains("fog", ignoreCase = true) -> R.drawable.ic_foggy
        else -> {
            if (isNight) R.drawable.ic_clear_moon_stars else R.drawable.ic_sunny
        }
    }
}

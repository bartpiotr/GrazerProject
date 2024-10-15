package com.bartekjobhunt.grazerproject.ui.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bartekjobhunt.grazerproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(viewModel: UsersViewModel = hiltViewModel()) {
    val usersState by viewModel.usersState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Users") }
            )
        }
    ) { innerPadding ->
        when (usersState) {
            is UsersState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(innerPadding))
            }
            is UsersState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items((usersState as UsersState.Success).users) { user ->
                        ElevatedCard(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = user.profileImageUrl,
                                    contentDescription = stringResource(
                                        R.string.profile_photo_of,
                                        user.name
                                    ),
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(text = user.name)
                                    Text(text = stringResource(
                                        R.string.age,
                                        viewModel.calculateAge(user.dateOfBirth)
                                    ))
                                }
                            }
                        }
                    }
                }
            }
            is UsersState.Error -> {
                Text(
                    text = (usersState as UsersState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

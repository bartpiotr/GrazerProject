package com.bartekjobhunt.grazerproject.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bartekjobhunt.grazerproject.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (loginState) {
            LoginState.Loading -> {
                CircularProgressIndicator()
            }

            LoginState.Success -> {
                onLoginSuccess()
            }

            is LoginState.Error -> {
                // normally we'd have a style for error text so it's consistent across the app
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = Color.Red,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )
            }

            else -> {}
        }
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                // TODO - the logic below should be in a testable class, not viewmodel though since it's using resources
                val image =
                    if (viewModel.passwordVisible)
                        R.drawable.visibility_on
                    else
                        R.drawable.visibility_off
                val description = if (viewModel.passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { viewModel.updatePasswordVisible( !viewModel.passwordVisible) }) {
                    Icon(imageVector = ImageVector.vectorResource(image), description)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.login()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = loginState !is LoginState.Loading
        ) {
            Text(stringResource(R.string.login))
        }
    }
}

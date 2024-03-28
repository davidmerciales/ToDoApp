package com.example.mvvmtodo.presenter.ui.screen.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            placeholder = {
                Text(text = "Email")
            },
            value = viewModel.state.email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                viewModel.state.email = it
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "email_icon")
            })

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            placeholder = {
                Text(text = "Password")
            },
            value = viewModel.state.password,
            onValueChange = {
                viewModel.state.password = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (viewModel.state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "password_icon")
            },
            trailingIcon = {
                val passwordToggle =
                    if (viewModel.state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff

                IconButton(onClick = {
                    viewModel.state.isPasswordVisible = !viewModel.state.isPasswordVisible
                }) {
                    Icon(imageVector = passwordToggle, contentDescription = "password_toggle")
                }
            })

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(Color(0xFF3439a1), RoundedCornerShape(8.dp))
                .clickable {
                    Log.d("LoginScreen: ", "LoginScreen: ")
                    viewModel.onEvent(LoginContract.LoginEvent.OnLoginClick)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(15.dp),
                text = "LOGIN",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}
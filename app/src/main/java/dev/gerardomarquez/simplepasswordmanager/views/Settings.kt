package dev.gerardomarquez.simplepasswordmanager.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import java.security.SecureRandom

@Composable
fun Settings(
    modifier: Modifier,
    navigateToLogin: () -> Unit,
){
    var salt by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Constants.HEADER_SETTINGS,
            fontSize = Constants.SIZE_TEXT_TITLE.sp
        )
        Text(
            text = Constants.TXT_SETTINGS_SALT
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            OutlinedTextField(
                value = salt,
                onValueChange = { it -> salt = it },
                label = { Text(text = Constants.TXT_SETTINGS_SALT) }
            )
            IconButton(
                onClick = { salt = ByteArray(16).also { SecureRandom().nextBytes(it) }.joinToString("") { "%02x".format(it) } }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.refresh_square_svgrepo_com),
                    contentDescription = Constants.DESCRIPTION_ICON_REFRESH_SALT
                )
            }
        }
        OutlinedButton(
            onClick = navigateToLogin,
            shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp)
        ) {
            Text(
                text = Constants.TEXT_BUTTON_CANCEL
            )
        }
    }
}
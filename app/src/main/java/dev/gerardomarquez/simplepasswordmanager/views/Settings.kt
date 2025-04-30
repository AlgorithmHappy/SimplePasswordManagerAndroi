package dev.gerardomarquez.simplepasswordmanager.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModel
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import dev.gerardomarquez.simplepasswordmanager.utils.hexStringToByteArray
import java.security.SecureRandom

@Composable
fun Settings(
    modifier: Modifier,
    viewModel: PasswordsInformationsViewModel,
    navigateToLogin: () -> Unit,
){
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                modifier = Modifier.weight(0.85f),
                value = viewModel.stateSalt,
                onValueChange = { it -> viewModel.changeSalt(salt = it) },
                singleLine = true,
                label = { Text(text = Constants.TXT_SETTINGS_SALT) }
            )
            IconButton(
                modifier = Modifier.weight(0.15f),
                onClick = {
                    viewModel.changeSalt(
                        salt = ByteArray(16).also {
                        SecureRandom().nextBytes(it)
                        }.joinToString("") {
                            "%02x".format(it)
                        }
                    )
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.refresh_square_svgrepo_com),
                    contentDescription = Constants.DESCRIPTION_ICON_REFRESH_SALT
                )
            }
        }
        Text(
            text = Constants.TXT_SETTINGS_SALT_SELECT
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 30.dp) )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(viewModel.stateListSalt.size){
                Text(
                    modifier = Modifier
                        .clickable {
                            viewModel.changeSalt(salt = viewModel.stateListSalt.get(it) )
                        },
                    text = viewModel.stateListSalt.get(it)
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 30.dp) )
        Text(
            modifier = Modifier.padding(horizontal = 30.dp),
            text = Constants.TXT_SETTINGS_SALT_WARNING,
            color = Color.Red,
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedButton(
                onClick = navigateToLogin,
                shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp)
            ) {
                Text(
                    text = Constants.TEXT_BUTTON_CANCEL
                )
            }
            OutlinedButton(
                onClick = {
                    try {
                        hexStringToByteArray(viewModel.stateSalt) // Validar si es correcto el string
                        viewModel.saveOneSalt(context = context, salt = viewModel.stateSalt )
                        navigateToLogin()
                    } catch (e: Exception) { // Si truena en la validacion
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                },
                shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp)
            ) {
                Text(
                    text = Constants.TEXT_BUTTON_SAVE_SALT
                )
            }
        }
    }
}
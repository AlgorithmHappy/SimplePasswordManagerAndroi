package dev.gerardomarquez.simplepasswordmanager.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.utils.Constants

/**
 * Metodo principal donde se encuentra toda la estructura y los datos que se utilizan para la vista
 * de insertar datos
 * @param modifier Modificador que se jalara del metodo padre
 */
@Composable
fun DataInsert(modifier: Modifier){
    var title by rememberSaveable { mutableStateOf(value = String()) }
    var user by rememberSaveable { mutableStateOf(value = String()) }
    var password by rememberSaveable { mutableStateOf(value = String()) }
    var token by rememberSaveable { mutableStateOf(value = String()) }
    var comments by rememberSaveable { mutableStateOf(value = String()) }
    var url by rememberSaveable { mutableStateOf(value = String()) }
    var email by rememberSaveable { mutableStateOf(value = String()) }
    var phone by rememberSaveable { mutableStateOf(value = String()) }

    Column(
        modifier = Modifier // Este modificador sera el que se pasa como argumento, se tendra que modificar mas adelante
            .fillMaxSize()
            .padding(horizontal = Constants.DP_PADDING.dp, vertical = Constants.DP_PADDING.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_A_TENTH),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = Constants.HEADER_DATA_INSERT,
                fontSize = Constants.SIZE_TEXT_TITLE.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_MAIN_INSERT),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(Constants.WEIGHT_LAYOUT_A_MEDIUM),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_TITLE
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_USER
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_PASSWORD
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_SECRED_CODE_TOKEN
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_COMMENTS
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_URL
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_EMAIL
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_PHONE_NUMBER
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(Constants.WEIGHT_LAYOUT_A_MEDIUM),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInput(
                        placeHolder = Constants.DESCRIPTION_DATA_INSERT_TITLE,
                        dataInput = title,
                        onDataInputChange = {title = it}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInput(
                        placeHolder = Constants.DESCRIPTION_DATA_INSERT_USER,
                        dataInput = user,
                        onDataInputChange = {user = it}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataPrivateInput(
                        placeHolder = Constants.DESCRIPTION_DATA_INSERT_PASSWORD,
                        dataInput = password,
                        onDataInputChange = {password = it}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataPrivateInput(
                        placeHolder = Constants.DESCRIPTION_DATA_INSERT_TOKEN,
                        dataInput = token,
                        onDataInputChange = {token = it}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInput(
                        placeHolder = Constants.DESCRIPTION_DATA_INSERT_COMMENTS,
                        dataInput = comments,
                        onDataInputChange = {comments = it}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInput(
                        placeHolder = Constants.DESCRIPTION_DATA_INSERT_URL,
                        dataInput = url,
                        onDataInputChange = {url = it}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInput(
                        placeHolder = Constants.DESCRIPTION_DATA_INSERT_EMAIL_RECOVERY,
                        dataInput = email,
                        onDataInputChange = {email = it}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataPhoneInput(
                        placeHolder = Constants.DESCRIPTION_DATA_INSERT_PHONE_NUMBER,
                        dataInput = phone,
                        onDataInputChange = {phone = it}
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_A_TENTH)
                .padding(
                    top = Constants.DP_PADDING_INSERTS_BUTTONS.dp,
                    bottom = Constants.DP_PADDING_INSERTS_BUTTONS.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.45f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ButtonCancel()
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.45f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonOk()
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_A_TENTH),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            TextInstructions()
        }
    }
}

/**
 * TextField para ingresar todos los datos necesarios para la cuenta que quiere guardar
 * @param placeHolder Texto de ejemplo que se mostrara en el textfield
 * @param dataInput Texto que ingresara el usuario en este elemento
 * @param onDataInputChange Metodo que se ejecutara para que se actualize y tome la informacion que
 * el usuario ingresa correctamente
 */
@Composable
fun UserDataInput(placeHolder: String, dataInput: String, onDataInputChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = dataInput,
        onValueChange = onDataInputChange,
        placeholder = {Text(text = placeHolder, fontSize = 10.sp)}
    )
}

/**
 * TextField para que el usuario pueda ingresar datos privados, este elemento oculta o muestra el
 * texto ingresado dependiendo si presiona el boton del ojo o no
 * @param placeHolder Texto de ejemplo que se mostrara en el textfield
 * @param dataInput Texto que ingresara el usuario en este elemento
 * @param onDataInputChange Metodo que se ejecutara para que se actualize y tome la informacion que
 * el usuario ingresa correctamente
 */
@Composable
fun UserDataPrivateInput(placeHolder: String, dataInput: String, onDataInputChange: (String) -> Unit) {
    var textVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = dataInput,
        onValueChange = onDataInputChange,
        placeholder = { Text(text = placeHolder, fontSize = 10.sp) },
        visualTransformation = if (textVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val icon = if (textVisible) painterResource(R.drawable.eye_closed_svgrepo_com) else painterResource(R.drawable.eye_svgrepo_com)
            IconButton(
                onClick = { textVisible = !textVisible }
            ) {
                Icon(
                    painter = icon,
                    contentDescription = Constants.DESCRIPTION_PASSWORD_VISIBILITY
                )
            }
        }
    )
}

/**
 * TextField para que el usuario ingrese texto que solo contenga numeros
 * @param placeHolder Texto de ejemplo que se mostrara en el textfield
 * @param dataInput Texto que ingresara el usuario en este elemento (solo podran ser numericos)
 * @param onDataInputChange Metodo que se ejecutara para que se actualize y tome la informacion que
 * el usuario ingresa correctamente
 */
@Composable
fun UserDataPhoneInput(placeHolder: String, dataInput: String, onDataInputChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = dataInput,
        onValueChange = onDataInputChange,
        placeholder = {Text(text = placeHolder, fontSize = 10.sp)},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

/**
 * Boton para insertar los datos ingresados en los elementos definidos en esta pantalla
 */
@Composable
fun ButtonOk(){
    OutlinedButton(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
        onClick = {
            Log.i("logica:", "Aqui se hara la logica de guardar los datos en SQLite")
        }
    ) {
        Text(text = Constants.TEXT_BUTTON_SAVE)
    }
}

/**
 * Boton para cancelar la accion de la pantalla
 */
@Composable
fun ButtonCancel(){
    OutlinedButton(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
        onClick = {
            Log.i("logica:", "Aqui se hara la logica de guardar los datos en SQLite")
        }
    ) {
        Text(text = Constants.TEXT_BUTTON_CANCEL)
    }
}

/**
 * Texto que indica que solo los campos con "*" son obligatorios
 */
@Composable
fun TextInstructions(){
    Text(
        text = Constants.TEXT_INSERT_VIEW_INSTRUCTIONS,
        textAlign = TextAlign.Center
    )
}

/**
 * Crea un dialogo por encima de toda la vista para indicar al usuario que se inserto el registro
 * correctamente
 * @param show Si es true se mostrara el dialogo en caso contrario se ocultara
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveAlertDialog(show: Boolean, onDismissRequest: () -> Unit){
    if(show) {
        BasicAlertDialog(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = onDismissRequest
        ) {
            Text(
                text = Constants.TEXT_ALERT_DIALOG_INSERT_OK
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true
)
fun DataInsertPreview(){
    DataInsert(modifier = Modifier.fillMaxSize())
}
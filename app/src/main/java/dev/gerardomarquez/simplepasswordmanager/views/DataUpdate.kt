package dev.gerardomarquez.simplepasswordmanager.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModel
import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations
import dev.gerardomarquez.simplepasswordmanager.utils.CharactersIncludedInPassword
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import dev.gerardomarquez.simplepasswordmanager.utils.passwordGenerator
import kotlinx.coroutines.delay

/**
 * Metodo principal donde se encuentra toda la estructura y los datos que se utilizan para la vista
 * de actualizacion de datos
 * @param modifier Modificador que se jalara del metodo padre
 * @param navigationController Objeto que gestiona la navegacion entre pantallas de la aplicacion
 */
@Composable
fun DataUpdate(
    modifier: Modifier,
    viewModel: PasswordsInformationsViewModel,
    idPasswordInformation: Int,
    navigateToMain: () -> Unit
){
    viewModel.selectedOnePasswordInformation(idPasswordInformation)

    var confirmationDialog by rememberSaveable { mutableStateOf(value = false) }
    var makePasswordDialog by rememberSaveable { mutableStateOf(value = false) }

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
                text = Constants.HEADER_DATA_UPDATE,
                fontSize = Constants.SIZE_TEXT_TITLE.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_MAIN_UPDATE),
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
                    UserDataInputUpdate(
                        placeHolder = Constants.DESCRIPTION_DATA_INPUT_TITLE,
                        dataInput = viewModel.state.selectedPassword.password_title,
                        onDataInputChange = {viewModel.changeTitle(id = idPasswordInformation, title = it)}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInputUpdate(
                        placeHolder = Constants.DESCRIPTION_DATA_INPUT_USER,
                        dataInput = viewModel.state.selectedPassword.username,
                        onDataInputChange = {viewModel.changeUsername(id = idPasswordInformation, username = it)}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataPrivateInputUpdate(
                        placeHolder = Constants.DESCRIPTION_DATA_INPUT_PASSWORD,
                        dataInput = viewModel.state.selectedPassword.password,
                        onDataInputChange = {viewModel.changePassword(id = idPasswordInformation, password = it) }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataPrivateInputUpdate(
                        placeHolder = Constants.DESCRIPTION_DATA_INPUT_TOKEN,
                        dataInput = viewModel.state.selectedPassword.token ?: "",
                        onDataInputChange = {viewModel.changeToken(id = idPasswordInformation, token = it)}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInputUpdate(
                        placeHolder = Constants.DESCRIPTION_DATA_INPUT_COMMENTS,
                        dataInput = viewModel.state.selectedPassword.notes ?: "",
                        onDataInputChange = {viewModel.changeNotes(id = idPasswordInformation,  notes = it)}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInputUpdate(
                        placeHolder = Constants.DESCRIPTION_DATA_INPUT_URL,
                        dataInput = viewModel.state.selectedPassword.url ?: "",
                        onDataInputChange = {viewModel.changeWebPage(id = idPasswordInformation, url = it)}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataInputUpdate(
                        placeHolder = Constants.DESCRIPTION_DATA_INPUT_EMAIL_RECOVERY,
                        dataInput = viewModel.state.selectedPassword.email ?: "",
                        onDataInputChange = {viewModel.changeEmail(id = idPasswordInformation, email = it)}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Constants.WEIGHT_LAYOUT_ONE_EIGHTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserDataPhoneInputUpdate(
                        placeHolder = Constants.DESCRIPTION_DATA_INPUT_PHONE_NUMBER,
                        dataInput = viewModel.state.selectedPassword.phone?.toString() ?: "",
                        onDataInputChange = {viewModel.changePhoneNumber(id = idPasswordInformation, phoneNumber = it)}
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
                    .weight(0.3f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ButtonCancelUpdate(
                    onClick = navigateToMain
                )
            }
            Spacer(modifier = Modifier.weight(0.01f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.36f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedButton(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                    onClick = { makePasswordDialog = true}
                ) {
                    Text(text = Constants.TEXT_PASSWORD_GENERATOR)
                }
            }
            Spacer(modifier = Modifier.weight(0.01f))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.32f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonOkUpdate(
                    onClick = {
                        val updatePasswordInformation = viewModel.state.selectedPassword.copy()
                        viewModel.saveOnePasswordInformation(
                            updatePasswordInformation
                        )
                        confirmationDialog = true
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_A_TENTH),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            TextInstructionsUpdate()
        }
    }
    SaveAlertDialogUpdate(
        show = confirmationDialog,
        onDismissRequest = {
            confirmationDialog = false
            navigateToMain()
        }
    )
    DialogPasswordGeneratorUpdate(
        show = makePasswordDialog,
        useButtonOnRequest = {
                it ->
            viewModel.changePassword(id = idPasswordInformation, password = it)
        },
        onDismissRequest = {
            makePasswordDialog = false
        }
    )
}

/**
 * TextField para ingresar todos los datos necesarios para la cuenta que quiere actualizar
 * @param placeHolder Texto de ejemplo que se mostrara en el textfield
 * @param dataInput Texto que ingresara el usuario en este elemento
 * @param onDataInputChange Metodo que se ejecutara para que se actualize y tome la informacion que
 * el usuario ingresa correctamente
 */
@Composable
fun UserDataInputUpdate(placeHolder: String, dataInput: String, onDataInputChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = dataInput,
        onValueChange = onDataInputChange,
        placeholder = { Text(text = placeHolder, fontSize = Constants.SIZE_TEXT_PLACE_HOLDER.sp) }
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
fun UserDataPrivateInputUpdate(placeHolder: String, dataInput: String, onDataInputChange: (String) -> Unit) {
    var textVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = dataInput,
        onValueChange = onDataInputChange,
        placeholder = { Text(text = placeHolder, fontSize = Constants.SIZE_TEXT_PLACE_HOLDER.sp) },
        visualTransformation = if (textVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val icon = if (textVisible) painterResource(R.drawable.eye_closed_svgrepo_com) else painterResource(
                R.drawable.eye_svgrepo_com)
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
fun UserDataPhoneInputUpdate(placeHolder: String, dataInput: String, onDataInputChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = dataInput,
        onValueChange = onDataInputChange,
        placeholder = { Text(text = placeHolder, fontSize = Constants.SIZE_TEXT_PLACE_HOLDER.sp) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

/**
 * Boton para insertar los datos ingresados en los elementos definidos en esta pantalla
 */
@Composable
fun ButtonOkUpdate(onClick: () -> Unit){
    OutlinedButton(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
        onClick = onClick
    ) {
        Text(text = Constants.TEXT_BUTTON_UPDATE)
    }
}

/**
 * Boton para cancelar la accion de la pantalla
 * @param onClick Metodo que se ejecuta al presionar el boton
 */
@Composable
fun ButtonCancelUpdate(onClick: () -> Unit){
    OutlinedButton(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
        onClick = onClick
    ) {
        Text(text = Constants.TEXT_BUTTON_CANCEL)
    }
}

/**
 * Texto que indica que solo los campos con "*" son obligatorios
 */
@Composable
fun TextInstructionsUpdate(){
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
fun SaveAlertDialogUpdate(show: Boolean, onDismissRequest: () -> Unit){
    if(show) {
        BasicAlertDialog(
            modifier = Modifier
                .fillMaxWidth(fraction = Constants.WEIGHT_LAYOUT_DIALOGS_WIDTH)
                .fillMaxHeight(fraction = Constants.WEIGHT_LAYOUT_DIALOGS_HEIGHT),
            onDismissRequest = onDismissRequest,
        ) {
            Column(
                modifier = Modifier // Este modificador sera el que se pasa como argumento, se tendra que modificar mas adelante
                    .clip(RoundedCornerShape(Constants.DP_ROUNDED_DIALOGS.dp) )
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(
                        horizontal = Constants.DP_PADDING_DIALOGS.dp,
                        vertical = Constants.DP_PADDING_DIALOGS.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = Constants.TEXT_ALERT_DIALOG_UPDATE_OK,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}

/**
 * Crea un dialogo por encima de toda la vista para indicar al usuario que se inserto el registro
 * correctamente
 * @param show Si es true se mostrara el dialogo en caso contrario se ocultara
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogPasswordGeneratorUpdate(show: Boolean, useButtonOnRequest: (String) -> Unit, onDismissRequest: () -> Unit) {
    var lowerCase by rememberSaveable { mutableStateOf(value = true) }
    var upperCase by rememberSaveable { mutableStateOf(value = false) }
    var numbers by rememberSaveable { mutableStateOf(value = false) }
    var specialCharacters by rememberSaveable { mutableStateOf(value = false) }
    val validateCheckBox: (Boolean, Boolean, Boolean, Boolean,) -> Boolean = {
            lowerCase, upperCase, numbers, specialCharacters ->
        !(!lowerCase && !upperCase && !numbers && !specialCharacters) // Debe haber al menos un tipo de caracter elegido
    }
    var sizeSelected by rememberSaveable { mutableStateOf(value = 8) }
    var password by rememberSaveable {
        mutableStateOf(
            value = passwordGenerator(
                size = sizeSelected,
                charactersIncluded = CharactersIncludedInPassword(
                    lowerCase = lowerCase,
                    upperCase = upperCase,
                    numbers = numbers,
                    specialCharacters = specialCharacters
                )
            )
        )
    }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var clipboardCopy by rememberSaveable { mutableStateOf(value = false) }
    val context = LocalContext.current

    if(show) {
        BasicAlertDialog(
            modifier = Modifier
                .fillMaxWidth(fraction = Constants.WEIGHT_LAYOUT_PASSWORD_DIALOG_WIDTH)
                .fillMaxHeight(fraction = Constants.WEIGHT_LAYOUT_PASSWORD_DIALOG_HEIGHT),
            onDismissRequest = onDismissRequest,
        ) {
            Column(
                modifier = Modifier // Este modificador sera el que se pasa como argumento, se tendra que modificar mas adelante
                    .clip(RoundedCornerShape(Constants.DP_ROUNDED_DIALOGS.dp))
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(
                        horizontal = Constants.DP_PADDING_DIALOGS.dp,
                        vertical = Constants.DP_PADDING_DIALOGS.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = Constants.TEXT_ALERT_DIALOG_PASSWORD_GENERATOR,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.weight(0.8f)
                    ) {
                        UserDataPrivateInputInsert(
                            placeHolder = "",
                            dataInput = password,
                            onDataInputChange = { password = it }
                        )
                    }
                    Column(modifier = Modifier.weight(0.2f) ) {
                        IconButton(
                            onClick = {
                                password = passwordGenerator(
                                    size = sizeSelected,
                                    charactersIncluded = CharactersIncludedInPassword(
                                        lowerCase = lowerCase,
                                        upperCase = upperCase,
                                        numbers = numbers,
                                        specialCharacters = specialCharacters
                                    )
                                )
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.refresh_square_svgrepo_com),
                                contentDescription = Constants.DESCRIPTION_ICON_REFRESH
                            )
                        }
                    }
                }
                Text(
                    text = Constants.TEXT_ALERT_DIALOG_PASSWORD_GENERATOR_SIZE,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    RadioButton(
                        selected = sizeSelected == 8,
                        onClick = { sizeSelected = 8 }
                    )
                    Text(text = "8")
                    RadioButton(
                        selected = sizeSelected == 10,
                        onClick = { sizeSelected = 10 }
                    )
                    Text(text = "10")
                    RadioButton(
                        selected = sizeSelected == 12,
                        onClick = { sizeSelected = 12 }
                    )
                    Text(text = "12")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = Constants.TXT_LOWER_CASE_CHEK)
                    Checkbox(
                        checked = lowerCase,
                        onCheckedChange = {
                            if(validateCheckBox(it, upperCase, numbers, specialCharacters) ){
                                lowerCase = it
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = Constants.TXT_UPPER_CASE_CHEK)
                    Checkbox(
                        checked = upperCase,
                        onCheckedChange = {
                            if (validateCheckBox(lowerCase, it, numbers, specialCharacters)) {
                                upperCase = it
                            }
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = Constants.TXT_NUMBERS_CHEK)
                    Checkbox(
                        checked = numbers,
                        onCheckedChange = {
                            if (validateCheckBox(lowerCase, upperCase, it, specialCharacters)) {
                                numbers = it
                            }
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = Constants.TXT_SPECIAL_CHARACTERS_CHEK)
                    Checkbox(
                        checked = specialCharacters,
                        onCheckedChange = {
                            if (validateCheckBox(lowerCase, upperCase, numbers, it)) {
                                specialCharacters = it
                            }
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            useButtonOnRequest(password)
                            onDismissRequest()
                        }
                    ) {
                        Text(text = Constants.TXT_USE_PASSWORD_GENERATED)
                    }
                    OutlinedButton(
                        onClick = {
                            clipboardManager.setText(annotatedString = AnnotatedString(text = password) )
                            clipboardCopy = true
                            Toast.makeText(context, "Password copiado, se borrara en 30 segundos", Toast.LENGTH_SHORT).show()
                            onDismissRequest()
                        }
                    ) {
                        Text(text = Constants.TXT_COPY_PASSWORD_GENERATED)
                    }
                }
            }
        }
    }
    if (clipboardCopy) {
        LaunchedEffect(Unit) {
            delay(30_000L)
            clipboardManager.setText(AnnotatedString(""))
            clipboardCopy = false
        }
    }
}

/*@Composable
@Preview(
    showBackground = true
)
fun DataUpdatePreview(){
    val navigationController = rememberNavController()
    DataUpdate(
        modifier = Modifier.fillMaxSize(),
        navigationController = navigationController
    )
}*/
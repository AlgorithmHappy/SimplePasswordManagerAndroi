package dev.gerardomarquez.simplepasswordmanager.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import dev.gerardomarquez.simplepasswordmanager.ListStatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.StatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModel
import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import kotlinx.coroutines.delay

/**
 * Metodo principal que ordena todos los elementos y variables que contendra la pantalla MAIN, que
 * sera la pantalla que enlista todas las contraseñas guardadas
 * @param modifier Modificador que contendra el padding y el maximo de pantalla de quien lo mande
 * a llamar
 * @param navigationController Objeto que gestiona la navegacion entre pantallas de la aplicacion
 */
@Composable
fun Main(
    modifier: Modifier,
    viewModel: PasswordsInformationsViewModel,
    navigateToFilters: () -> Unit,
    navigateToInsert: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToUpdate: (Int) -> Unit,
){
    val density = LocalDensity.current // Obtener la densidad de la pantalla
    val context = LocalContext.current
    var textSearch by rememberSaveable { mutableStateOf( value = String() ) }
    var information: ListStatePaswordInformation = viewModel.state
    var confirmationDialog by rememberSaveable { mutableStateOf(value = false)}
    var layoutCoordinates: LayoutCoordinates? = null
    var showDialogDelate by rememberSaveable { mutableStateOf(value = false)}
    var deletePaswordInformation by rememberSaveable { mutableStateOf(value = 0) }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var clipboardCopy by rememberSaveable { mutableStateOf(value = false) }

    Column(
        modifier = modifier // Este modificador sera el que se pasa como argumento, se tendra que modificar mas adelante
            .fillMaxSize()
            .padding(
                horizontal = Constants.DP_PADDING.dp,
                vertical = Constants.DP_PADDING.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_MAIN_SOME_ROWS),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            HeaderMain()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_MAIN_SOME_ROWS),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            SearchBarMain(
                modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_SEARCH_BAR_MAIN),
                placeHolder = Constants.DESCRIPTION_DATA_INPUT_SEARCH_BAR,
                dataInput = textSearch,
                onDataInputChange = {
                    textSearch = it
                    viewModel.filterList(searchText = textSearch)
                }
            )
            Spacer(
                modifier = Modifier.weight(weight = Constants.WEIGHT_LAYOUT_MAIN_FILTER)
            )
            IconFilter(
                modifier = Modifier
                    .weight(Constants.WEIGHT_LAYOUT_ICON_FILTER)
                    .clickable(onClick = navigateToFilters)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_MAIN_MIDLE_ROW)
                .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp)
                .onGloballyPositioned {
                    cordinates -> layoutCoordinates = cordinates
                }
        ){
            LazyColumn(
                modifier = Modifier
                    .weight(Constants.WEIGHT_LAYOUT_FULL)
            ){
                items(information.listPaswordInformation.size){
                    var showMenuLongPress by rememberSaveable { mutableStateOf(value = false)}
                    var menuOffset: Offset = Offset.Zero
                    var idSelectedPassword: Int = information.listPaswordInformation.get(it).id
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Constants.DP_HEIGHT_PASSWORDS_DROPDOWN.dp)
                            .pointerInput(Unit){
                                detectTapGestures(
                                    onLongPress = {
                                            offset -> layoutCoordinates?.let { layout ->
                                        val positionInWindow = layout.localToWindow(offset) // Convierte la posición relativa a absoluta
                                        menuOffset = positionInWindow
                                        showMenuLongPress = true // Activa el menú al hacer touch sostenido
                                    }
                                    }
                                )
                            }
                    ){
                        LongPressMenu(
                            showMenu = showMenuLongPress,
                            menuOffset = menuOffset,
                            density = density,
                            onDismissRequest = {
                                showMenuLongPress = false
                            },
                            onClickCopyUser = {
                                clipboardManager.setText(annotatedString = AnnotatedString(information.listPaswordInformation.get(it).username ) )
                                clipboardCopy = true
                                Toast.makeText(context, "Usuario copiado, se borrara en 30 segundos", Toast.LENGTH_SHORT).show()
                                showMenuLongPress = false
                            },
                            onClickCopyPassword = {
                                clipboardManager.setText(annotatedString = AnnotatedString(information.listPaswordInformation.get(it).password ) )
                                clipboardCopy = true
                                Toast.makeText(context, "Contraseña copiada, se borrara en 30 segundos", Toast.LENGTH_SHORT).show()
                                showMenuLongPress = false
                            },
                            onClickCopyToken = {
                                clipboardManager.setText(annotatedString = AnnotatedString(information.listPaswordInformation.get(it).token?:"Sin token" ) )
                                clipboardCopy = true
                                Toast.makeText(context, "Token copiado, se borrara en 30 segundos", Toast.LENGTH_SHORT).show()
                                showMenuLongPress = false
                            }
                        )
                        InformationPasswordDropDown(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp),
                            information = information.listPaswordInformation.get(it),
                            onClickOk = { navigateToUpdate(idSelectedPassword) },
                            onClickDelate = {
                                deletePaswordInformation = idSelectedPassword
                                showDialogDelate = true
                            }
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_MAIN_SOME_ROWS)
        ){
            ButtonInsert(
                modifier = Modifier
                    .weight(Constants.WEIGHT_LAYOUT_MAIN_BUTTONS)
                    .fillMaxHeight(),
                onClick = navigateToInsert
            )
            Spacer(
                modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
            )
            ButtonSaveFile(
                modifier = Modifier
                    .weight(Constants.WEIGHT_LAYOUT_MAIN_BUTTONS)
                    .fillMaxHeight(),
                onClick = {
                    viewModel.saveTempDatabaseEncrypted(
                        password = viewModel.stateClearPasswordDb
                    )
                    confirmationDialog = true
                    //viewModel.deleteTempDatabase(context = context) // Se borra para que no quede
                    // cache ni algun archivo en claro temporal   sd
                }
            )
        }
    }

    DialogMain(
        show = confirmationDialog,
        onClickOk = {
            confirmationDialog = false
            navigateToLogin()
        },
        onDismissRequest = {
            confirmationDialog = false
            navigateToLogin()
        }
    )

    DialogDelateMain(
        show = showDialogDelate,
        onOkRequest = {
            val delete = PasswordsInformations(
                id = deletePaswordInformation,
                password_title = "",
                username = "",
                password = "",
                token = null,
                email = null,
                phone = null,
                url = null,
                notes = null
            )
            viewModel.deleteOnePasswordInformation(passwordInformation = delete)
            showDialogDelate = false
            deletePaswordInformation = 0
        },
        onDismissRequest = {
            showDialogDelate = false
            deletePaswordInformation = 0
        }
    )

    if (clipboardCopy) {
        LaunchedEffect(Unit) {
            delay(30_000L)
            clipboardManager.setText(AnnotatedString(""))
            clipboardCopy = false
        }
    }
}

/**
 * Metodo del encabezado de la pantalla
 */
@Composable
fun HeaderMain(){
    Text(
        text = Constants.TEXT_MAIN,
        fontSize = Constants.SIZE_TEXT_TITLE.sp,
        textAlign = TextAlign.Center
    )
}

/**
 * Barra de busqueda para buscar entre todos los listados de contraseñas
 * @param placeHolder Texto de ejemplo que se mostrara
 * @param dataInput Texto que va ingresando el usuario
 * @param onDataInputChange Metodo que va actualizando lo que va ingresando el usuario
 */
@Composable
fun SearchBarMain(modifier: Modifier, placeHolder: String, dataInput: String, onDataInputChange: (String) -> Unit){
    OutlinedTextField(
        modifier = modifier,
        value = dataInput,
        onValueChange = onDataInputChange,
        placeholder = { Text(text = placeHolder, fontSize = Constants.SIZE_TEXT_PLACE_HOLDER.sp) },
        trailingIcon = {
            val icon = painterResource(R.drawable.minimalistic_magnifer_svgrepo_com)
            Icon(
                modifier = Modifier.fillMaxHeight(fraction = Constants.WEIGHT_LAYOUT_A_MEDIUM),
                painter = icon,
                contentDescription = Constants.DESCRIPTION_ICON_SEARCH
            )
        }
    )
}

/**
 * Icono que al dar click mostrara la pantalla "views/Filters"
 * @param modifier Modificador que debera ser clikeable para abrir la pantalla requerida
 */
@Composable
fun IconFilter(modifier: Modifier){
    val icon = painterResource(R.drawable.filter_svgrepo_com)
    Icon(
        modifier = modifier,
        painter = icon,
        contentDescription = Constants.DESCRIPTION_ICON_FILTER
    )
}

/**
 * Boton que insertara el registro en la base de datos temporal
 * @param modifier Modificador para posicionar los 2 botones de manera uniforme desde el padre
 * @param onClick Metodo que insertara en la base de datos temporal
 */
@Composable
fun ButtonInsert(modifier: Modifier, onClick: () -> Unit){
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
        onClick = onClick
    ) {
        Text(
            text = Constants.TEXT_BUTTON_NEW_INSERT
        )
    }
}

/**
 * Boton que guardara los cambios de la base de datos, pasara de la base de datos temporal a
 * sobreescribir la que se abrio en el login
 * @param modifier Modificador para posicionar los 2 botones de manera uniforme desde el padre
 * @param onClick Metodo que Ejecutara el guardado
 */
@Composable
fun ButtonSaveFile(modifier: Modifier, onClick: () -> Unit){
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
        onClick = onClick
    ) {
        Text(
            text = Constants.TEXT_BUTTON_SAVE_FILE
        )
    }
}

/**
 * Elemento grafico de dropdown para mostrar toda la informacion de las contraseñas
 * @param options Lista de opciones que contendra el dropdown, esta sera la lista de todos los paths
 * de las bases de datos encriptadas SQLite que tendra el archivo de propiedades
 * @param selectedOption Opcion del dropdown seleccionada por el usuario
 * @param onOptionSelected Funcion que se ejecutara al dar click en alguna de las opciones del
 * dropdown
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationPasswordDropDown(
    modifier: Modifier,
    information: StatePaswordInformation,
    onClickOk: () -> Unit,
    onClickDelate: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // Encabezado
        Row(
            modifier = Modifier
                .menuAnchor()
                .fillMaxSize()
                .clip(RoundedCornerShape(Constants.DP_ROUNDED_ROW.dp))
                .background(MaterialTheme.colorScheme.primary.copy(0.5f)) // Cambiar color
                .padding(Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconUp = painterResource(R.drawable.alt_arrow_up_svgrepo_com)
            val iconDown = painterResource(R.drawable.alt_arrow_down_svgrepo_com)
            // Título
            Text(
                text = information.password_title,
                modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_FULL),
                fontSize = Constants.SIZE_TEXT_TITLE_DROPDOWN.sp,
                fontWeight = FontWeight.Bold
            )

            // Icono de expansión
            Icon(
                modifier = Modifier.clickable { expanded = !expanded },
                painter = if (expanded) iconUp else iconDown,
                contentDescription = Constants.DESCRIPTION_ICON_DROPDOWN_LOGIN,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        // Contenido expandible
        ExposedDropdownMenu(
            modifier = Modifier
                .exposedDropdownSize(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Constants.DP_PADDING_INFORMATION_DROPDOWN.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp)
                ) {
                    Text(
                        text = Constants.TXT_USER_DROPDOWN,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_TITLE_DROPDOWN.sp,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
                    )
                    Text(
                        text = information.username,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_VALUES_DROPDOWN.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_PASSWORD_DROPDOWN,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_TITLE_DROPDOWN.sp,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
                    )

                    var textVisible by rememberSaveable { mutableStateOf(value = false) }
                    OutlinedTextField(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        value = information.password,
                        enabled = false,
                        onValueChange = { },
                        visualTransformation = if (textVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.TXT_SECRED_CODE_TOKEN,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_TITLE_DROPDOWN.sp,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
                    )
                    var textVisible by rememberSaveable { mutableStateOf(value = false) }
                    OutlinedTextField(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        value = information.token?:"",
                        enabled = false,
                        onValueChange = { },
                        visualTransformation = if (textVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp)
                ) {
                    Text(
                        text = Constants.TXT_COMMENTS,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_TITLE_DROPDOWN.sp,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
                    )
                    Text(
                        text = information.notes?:"",
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_VALUES_DROPDOWN.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp)
                ) {
                    Text(
                        text = Constants.TXT_URL,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_TITLE_DROPDOWN.sp,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
                    )
                    Text(
                        text = information.url?:"",
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_VALUES_DROPDOWN.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp)
                ) {
                    Text(
                        text = Constants.TXT_EMAIL,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_TITLE_DROPDOWN.sp,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
                    )
                    Text(
                        text = information.email?:"",
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_VALUES_DROPDOWN.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp)
                ) {
                    Text(
                        text = Constants.TXT_PHONE_NUMBER,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_TITLE_DROPDOWN.sp,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
                    )
                    Text(
                        text = information.phone?.toString()?:"",
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = Constants.SIZE_TEXT_VALUES_DROPDOWN.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_BUTTONS_DROPDOWN),
                        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                        onClick = onClickDelate
                    ){
                        Text(
                            text = Constants.TEXT_BUTTON_DELETE
                        )
                    }
                    Spacer(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_BUTTONS_SPACER_DROPDOWN)
                    )
                    Button(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_BUTTONS_DROPDOWN),
                        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                        onClick = onClickOk
                    ){
                        Text(
                            text = Constants.TEXT_BUTTON_UPDATE
                        )
                    }
                }
            }
        }
    }

}

/**
 * Dialogo que confirma qe se guardaron correctamente el archivo de base de datos
 * @param show Con esta variable se define si se mostrara o no el dialogo
 * @param onDismissRequest Con este metodo se cambia el valor de la variable show para ocultar el
 * dialogo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogMain(
    show: Boolean,
    onClickOk: () -> Unit,
    onDismissRequest: () -> Unit
){
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
                    text = Constants.TEXT_ALERT_DIALOG_MAIN_SAVE,
                    textAlign = TextAlign.Center
                )
                OutlinedButton(
                    shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                    onClick = onClickOk
                ) {
                    Text(
                        text = Constants.TEXT_BUTTON_OK,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Sub menu copiar usuario, contraseña o token
 */
@Composable
fun LongPressMenu(
    showMenu: Boolean,
    menuOffset: Offset,
    density: Density,
    onDismissRequest: () -> Unit,
    onClickCopyUser: () -> Unit,
    onClickCopyPassword: () -> Unit,
    onClickCopyToken: () -> Unit
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest,
        offset = DpOffset((menuOffset.x / density.density).dp, (menuOffset.y / density.density).dp)
    ) {
        DropdownMenuItem(
            text = { Text(Constants.TXT_COPY_USER) },
            onClick = onClickCopyUser
        )
        DropdownMenuItem(
            text = { Text(Constants.TXT_COPY_PASSWORD) },
            onClick = onClickCopyPassword
        )
        DropdownMenuItem(
            text = { Text(Constants.TXT_COPY_TOKEN) },
            onClick = onClickCopyToken
        )
    }
}

/**
 * Dialogo que confirma si se quiere eliminar el registro
 * @param show Con esta variable se define si se mostrara o no el dialogo
 * @param onDismissRequest Con este metodo se cambia el valor de la variable show para ocultar el
 * dialogo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDelateMain(show: Boolean, onOkRequest: () -> Unit, onDismissRequest: () -> Unit){
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
                    text = Constants.TEXT_ALERT_DIALOG_MAIN_DELATE,
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(
                        modifier = Modifier.weight(0.35f),
                        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                        onClick = onOkRequest
                    ){
                        Text("Si")
                    }
                    Spacer(modifier = Modifier.weight(0.30f))
                    Button(
                        modifier = Modifier.weight(0.35f),
                        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                        onClick = onDismissRequest
                    ){
                        Text("No")
                    }
                }
            }
        }
    }
}

/*@Composable
@Preview(
    showBackground = true
)
fun MainPreview(){
    val navigationController = rememberNavController()
    Main(
        modifier = Modifier.fillMaxSize(),
        navigationController = navigationController
    )
}*/
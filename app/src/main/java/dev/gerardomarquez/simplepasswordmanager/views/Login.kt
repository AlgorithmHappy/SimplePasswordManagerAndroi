package dev.gerardomarquez.simplepasswordmanager.views

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModel

/**
 * Metodo principal que encapsulara todos los elementos de la vista para realizar el login de la
 * aplicacion
 * @param modifier Modificador que se insertara desde el metodo padre
 * @param navigateT Objeto que gestiona la navegacion entre pantallas de la aplicacion
 */
@Composable
fun Login(
    modifier: Modifier,
    viewModel: PasswordsInformationsViewModel,
    navigateToMain: () -> Unit,
    navigateToFolderFileExplorer: (String) -> Unit,
    navigateToNewFileExplorer: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val context: Context = LocalContext.current
    var selectedFolderUriStr by rememberSaveable { mutableStateOf(value = "") }
    val folderPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        uri?.let {
            it ->
            selectedFolderUriStr = it.toString()
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            navigateToFolderFileExplorer(selectedFolderUriStr)
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = Constants.DP_PADDING.dp, vertical = Constants.DP_PADDING.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_A_TENTH),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Header();
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_NINE_TENTHS)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_A_TENTH))
                Row(
                    modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_THREE_TENTHS),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    LogoImage()
                }
                Row(
                    modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_A_TENTH),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_DROP_DOWN)
                    ){
                        DataBasesDropDown(
                            options = viewModel.stateListFilesNames,
                            selectedOption = if(viewModel.stateSelectedFileName.isBlank() ) Constants.GLOBAL_SELECCIONAR else viewModel.stateSelectedFileName,
                            onOptionSelected = {
                                viewModel.changeSelectedFileName(fileName = it)
                                val indexFileName: Int = viewModel.stateListFilesNames.indexOf(it)
                                val pathSelected: String = viewModel.stateListPaths[indexFileName]
                                viewModel.changeSelectedPath(path = pathSelected)
                            }
                        )
                    }
                    Column(
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_DROP_DOWN_BUTTON)
                    ){
                        ButtonOpen(
                            onClick = {
                                folderPickerLauncher.launch(input = null)
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_A_TENTH),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Password(
                        password = viewModel.stateClearPasswordDb,
                        onPasswordChange = {
                            viewModel.changeClearPasswordDb(password = it.trim() )
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_A_TENTH),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ButtonLogin(
                        onClick = {
                            if(viewModel.stateSelectedFileName.isBlank() || viewModel.stateSelectedFileName.equals(Constants.GLOBAL_SELECCIONAR) ) {
                                Toast.makeText(context, Constants.TEXT_SELECT_DATABASE, Toast.LENGTH_LONG).show()
                                return@ButtonLogin // Mata el proceso de accion del boton y no sigue con el codigo de abajo
                            }
                            viewModel.replaceEncryptedDBForTmpDB()
                            if(!viewModel.stateLockMain) {
                                viewModel.conectNewDatabase(context = context)
                                navigateToMain()
                            }
                        }
                    )
                }
                Spacer(
                    modifier = Modifier.fillMaxWidth().weight(Constants.SIZE_SPACE_WEIGHT_LAST_LOGIN)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_NEW_BUTTON),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = navigateToSettings
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings_svgrepo_com),
                            contentDescription = Constants.DESCRIPTION_ICON_SETTINGS
                        )
                    }
                    ButtonNew(
                        onClick = {
                            viewModel.changeClearPasswordDb(password = "") // Se limpia el password
                            navigateToNewFileExplorer()
                        }
                    )

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.errorDecryptUI.collect { showError ->
            if (showError) {
                Toast.makeText(context, Constants.TEXT_ERROR_PASSWORD, Toast.LENGTH_LONG).show()
            }
        }
    }
}

/**
 * Encabezado de la pantalla del login para poder visualizar las contraseñas
 */
@Composable
fun Header() {
    Text(
        text = Constants.HEADER_LOGIN,
        fontSize = Constants.SIZE_TEXT_TITLE.sp
    )
}

/**
 * Imagen de logo de la pantalla del login
 */
@Composable
fun LogoImage() {
    Icon(
        painter = painterResource(id = R.drawable.lock_keyhole_svgrepo_com),
        contentDescription = Constants.IMAGE_DESCRIPTION
    )
}

/**
 * Elemento grafico de dropdown para seleccionar las bases de datos de las contraseñas con las que
 * el usuario haya trabajado
 * @param options Lista de opciones que contendra el dropdown, esta sera la lista de todos los paths
 * de las bases de datos encriptadas SQLite que tendra el archivo de propiedades
 * @param selectedOption Opcion del dropdown seleccionada por el usuario
 * @param onOptionSelected Funcion que se ejecutara al dar click en alguna de las opciones del
 * dropdown
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataBasesDropDown(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = {
                val iconUp = painterResource(R.drawable.alt_arrow_up_svgrepo_com)
                val iconDown = painterResource(R.drawable.alt_arrow_down_svgrepo_com)
                Icon(
                    painter = if (expanded) iconUp else iconDown,
                    contentDescription = Constants.DESCRIPTION_ICON_DROPDOWN_LOGIN,
                    modifier = Modifier
                        .fillMaxHeight(fraction = Constants.WEIGHT_LAYOUT_HEIGTH_DROPDOWN_ARROW)
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

/**
 * Boton para abrir el explorador de archivos y buscar una base de datos en especifico
 */
@Composable
fun ButtonOpen(onClick: () -> Unit) {
    val icon = painterResource(R.drawable.folder_open_svgrepo_com)
    Icon(
        painter = icon,
        contentDescription = Constants.DESCRIPTION_ICON_OPEN,
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick)
    )
}

/**
 * TextField para ingresar la contraseña y poder abrir la base de datos
 * @param password Contraseña ingresada por el usuario
 * @param onPasswordChange Funcion lambda que se ejecutara en el onValueChange del elemento
 * OutlinedTextField, para que el password cambie dependiendo de lo que vaya ingresando el usuario
 */
@Composable
fun Password(password: String, onPasswordChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = Constants.DESCRIPTION_PASSWORD) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val icon = if (passwordVisible) painterResource(R.drawable.eye_closed_svgrepo_com) else painterResource(R.drawable.eye_svgrepo_com)
            IconButton(
                onClick = { passwordVisible = !passwordVisible }
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
 * Boton para ingresar
 */
@Composable
fun ButtonLogin(onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(fraction = Constants.WEIGHT_LAYOUT_WIDTH_LOGIN_BUTTON)
            .fillMaxHeight(fraction = Constants.WEIGHT_LAYOUT_HEIGTH_LOGIN_BUTTON),
        onClick = onClick,
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp)
    ) {
        Text(text = Constants.TEXT_LOGIN)
    }
}

/**
 * Boton para crear una nueva base de datos donde guardar los passwords
 */
@Composable
fun ButtonNew(onClick: () -> Unit) {
    val icon = painterResource(R.drawable.document_medicine_svgrepo_com)
    Icon(
        painter = icon,
        contentDescription = Constants.DESCRIPTION_ICON_NEW,
        modifier = Modifier
            .fillMaxHeight()
            .clickable(onClick = onClick)
    )
}

/*@Composable
@Preview(
    showBackground = true
)
fun LoginPreview(){
    val navigationController = rememberNavController()
    Login(
        modifier = Modifier
            .fillMaxSize(),
        navigationController
    )
    // Mas adelante hay que ver si podemos mandar error en el caso de que los datos esten erroneos
    // con un cuadro de dialogo o notificacion
}*/






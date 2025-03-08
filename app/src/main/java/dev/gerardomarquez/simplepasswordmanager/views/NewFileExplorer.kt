package dev.gerardomarquez.simplepasswordmanager.views

import android.content.Context
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.repositories.SettingsDataStore
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import dev.gerardomarquez.simplepasswordmanager.utils.getFolders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Metodo principal que ordena todos los elementos y variables que contendra la pantalla
 * NewFileExplorer, que sera la pantalla que enlista todas las carpetas para elegir una y ahi
 * guardar la nueva base de datos que almacenara las contraseñas
 * @param modifier Modificador que contendra el padding y el maximo de pantalla de quien lo mande
 * a llamar
 * @param navigateToLogin Metodo que se ejecutara al presionar el boton de cancelar
 */
@Composable
fun NewFileExplorerView(
    modifier: Modifier,
    navigateToLogin: () -> Unit
){
    var folders by rememberSaveable { mutableStateOf(getFolders().toMutableList() ) }
    var selectedFolder by rememberSaveable { mutableStateOf(value = Environment.getExternalStorageDirectory().absolutePath) }
    var showDialog by rememberSaveable { mutableStateOf(value = false) }
    var fileName by rememberSaveable { mutableStateOf(value = "") }
    val context: Context = LocalContext.current
    val listFlow = SettingsDataStore.getDatabasesPaths(context).collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .padding(Constants.DP_PADDING.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_MAIN_ROWS),
            verticalAlignment = Alignment.CenterVertically
        ){
            NewFileExplorerViewHeader(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_PATH_ROW),
            verticalAlignment = Alignment.CenterVertically
        ){
            NewFullPath(
                path = selectedFolder,
                modifier = Modifier
                    .fillMaxWidth(fraction = Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BACK_FOLDER_BUTTON)
                    .align(alignment = Alignment.CenterVertically)
            )
            BackFolder(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        if(!selectedFolder.equals(Environment.getExternalStorageDirectory().absolutePath) ) {
                            selectedFolder = selectedFolder.substringBeforeLast(Constants.STR_SLASH)
                            folders = getFolders(path = selectedFolder).toMutableList()
                        }
                    }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_MAIN_ROW)
                .padding(vertical = Constants.DP_SIZE_ROW_FILES_PADDING.dp)
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(folders.size){
                    it ->
                        NewSelectedFolder(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = Constants.DP_SIZE_ROW_FOLDERS.dp)
                                .padding(vertical = Constants.DP_SIZE_ROW_FOLDERS_PADDING.dp)
                                .clickable {
                                    selectedFolder =
                                        selectedFolder + Constants.STR_SLASH + folders[it]
                                    folders = getFolders(path = selectedFolder).toMutableList()
                                },
                            folderName = folders[it]
                        )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_MAIN_ROWS)
        ){
            NewButtonCancel(
                modifier = Modifier
                    .weight(weight = Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS)
                    .fillMaxHeight(),
                onClick = navigateToLogin
            )
            Spacer(
                modifier = Modifier
                    .weight(weight = Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS_SPACER)
            )
            NewButtonSelect(
                modifier = Modifier
                    .weight(weight = Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS)
                    .fillMaxHeight(),
                onClick = {
                    showDialog = true
                }
            )
        }
    }
    InsertNameNewFile(
        show = showDialog,
        fileName = fileName,
        onChangeFileName = {it -> fileName = it},
        onClickOk = {
            showDialog = false
            CoroutineScope(Dispatchers.IO).launch {
                val completePath: String = selectedFolder + Constants.STR_SLASH + fileName
                SettingsDataStore.saveDatabasesPaths(
                    context = context,
                    listDatabasesPaths = listFlow.value + completePath
                )
            }
            navigateToLogin()
        },
        onClickCancel = {
            showDialog = false
        }
    )
}

/**
 * Encabezado de la pantalla
 * @param Modificador para configurar el componente
 */
@Composable
fun NewFileExplorerViewHeader(modifier: Modifier){
    Text(
        modifier = modifier,
        text = Constants.HEADER_NEW_FILE_EXPLORER,
        textAlign = TextAlign.Center,
        fontSize = Constants.SIZE_TEXT_TITLE.sp
    )
}

/**
 * String que mostrara la ruta absoluta donde se guardara la nueva base de datos que se esta creando
 * @param path Ruta absoluta
 * @param modifier Para configurar el texto
 */
@Composable
fun NewFullPath(path: String, modifier: Modifier){
    Text(
        modifier = modifier,
        text = path,
        fontSize = Constants.SIZE_TEXT_PATH.sp,
        textAlign = TextAlign.Start
    )
}

/**
 * Componente que muestra el nombre de la carpeta que podria seleccionarse
 * @param modifier Para poder configurar el Row de este componente
 * @param folderName Nombre de la carpeta
 */
@Composable
fun NewSelectedFolder(modifier: Modifier, folderName: String){
    val icon = painterResource(R.drawable.folder_open_svgrepo_com)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.fillMaxHeight(),
            painter = icon,
            contentDescription = Constants.DESCRIPTION_FOLDER_SELECTED
        )
        Spacer(
            modifier = Modifier
                .width(width = Constants.DP_SIZE_ROW_FOLDERS_SPACER.dp)
        )
        Text(
            text = folderName
        )
    }
}

/**
 * Boton que al presionarlo guardara la nueva ruta de la nueva base de datos para las contraseñas
 * @param modifier Para configurar el componente
 * @param onClick Metodo que se ejecutara al presionar el boton
 */
@Composable
fun NewButtonSelect(modifier: Modifier, onClick: () -> Unit){
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp)
    ) {
        Text(
            text = Constants.TEXT_BUTTON_SELECT
        )
    }
}

/**
 * Boton que al presionarlo cancelara la accion
 * @param modifier Para configurar el componente
 * @param onClick Metodo que se ejecutara al presionar el boton
 */
@Composable
fun NewButtonCancel(modifier: Modifier, onClick: () -> Unit){
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp)
    ) {
        Text(
            text = Constants.TEXT_BUTTON_CANCEL
        )
    }
}

/**
 * Boton que al presionarlo vuelve atras en la carpeta
 * @param modifier Para configurar el componente
 * @param onClick Metodo que se ejecutara al presionar el boton
 */
@Composable
fun BackFolder(modifier: Modifier){
    val icon = painterResource(R.drawable.undo_left_square_svgrepo_com)
    Icon(
        painter = icon,
        contentDescription = Constants.DESCRIPTION_ICON_BACK_FOLDER,
        modifier = modifier
    )
}

/**
 * Dialogo con el que se ingresara el nombre de la nueva base de datos
 * @param show Con esta variable se define si se mostrara o no el dialogo
 * @param fileName Nombre de la nueva base de datos
 * @param onChangeFileName Con este metodo se guardara el nombre de la nueva base de datos
 * @param onClickOk Con este metodo se guardara el nombre de la nueva base de datos
 * @param onClickCancel Con este metodo se cancela la accion
 * dialogo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertNameNewFile(
    show: Boolean,
    fileName: String,
    onChangeFileName: (String) -> Unit,
    onClickOk: () -> Unit,
    onClickCancel: () -> Unit
){
    if(show) {
        BasicAlertDialog(
            modifier = Modifier
                .fillMaxWidth(fraction = Constants.WEIGHT_LAYOUT_DIALOGS_WIDTH)
                .fillMaxHeight(fraction = Constants.WEIGHT_LAYOUT_DIALOGS_HEIGHT),
            onDismissRequest = onClickCancel,
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
                    text = Constants.TEXT_ALERT_DIALOG_NEW_FILE,
                    textAlign = TextAlign.Center
                )
                TextField(
                    value = fileName,
                    onValueChange = onChangeFileName
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                        onClick = onClickCancel
                    ) {
                        Text(text = Constants.TEXT_BUTTON_CANCEL)
                    }
                    OutlinedButton(
                        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                        onClick = onClickOk
                    ) {
                        Text(text = Constants.TEXT_BUTTON_OK)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true
)
fun NewFileExplorerPreview() {
    NewFileExplorerView(
        modifier = Modifier
            .fillMaxSize(),
        navigateToLogin = {}
    )
}


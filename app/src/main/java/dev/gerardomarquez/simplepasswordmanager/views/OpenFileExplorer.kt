package dev.gerardomarquez.simplepasswordmanager.views

import android.content.Context
import android.content.pm.PackageManager
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import dev.gerardomarquez.simplepasswordmanager.utils.getFiles
import dev.gerardomarquez.simplepasswordmanager.utils.getFolders
import java.util.Arrays.asList
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import dev.gerardomarquez.simplepasswordmanager.repositories.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Metodo principal que ordena todos los elementos y variables que contendra la pantalla
 * NewFileExplorer, que sera la pantalla que enlista todas las carpetas para elegir una y ahi
 * guardar la nueva base de datos que almacenara las contraseñas
 * @param modifier Modificador que contendra el padding y el maximo de pantalla de quien lo mande
 * a llamar
 * @param navigateToLogin Metodo que se ejecutara al presionar el boton de cancelar y navegar al login
 */
@Composable
fun OpenFileExplorerView(
    modifier: Modifier,
    selectedFolderString: String,
    navigateToLogin: () -> Unit
){
    var mutableSelectUriStr by rememberSaveable { mutableStateOf(value = selectedFolderString) } // Se crea una variable auxiliar ya que la variable selectedFolderString no se puede modificar
    val basePathUriPermited by rememberSaveable {
        mutableStateOf(value = Environment.getExternalStorageDirectory().absolutePath +
                Constants.STR_SLASH +
                Uri.parse(mutableSelectUriStr).path?.replace(
                    Constants.GLOBAL_STR_URI_TO_PATH,
                    ""
                ) // Se crea el path actual a partir de la uri que permitido el usuario explorar en la carpeta
        )
    }
    val context: Context = LocalContext.current
    var currentPath by rememberSaveable { mutableStateOf(value = basePathUriPermited) }
    var selectedIndexFile by rememberSaveable { mutableStateOf(value = Constants.GLOBAL_NEGATIVE_NUMBER) }
    var foldersAndFiles by rememberSaveable { mutableStateOf(value = getFolders(currentPath) + getFiles(context = context, uri = Uri.parse(mutableSelectUriStr) ) ) }
    var fileName by rememberSaveable { mutableStateOf(value = "") }

    Column(
        modifier = modifier
            .padding(Constants.DP_PADDING.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_MAIN_ROWS),
            verticalAlignment = Alignment.CenterVertically
        ){
            OpenFileExplorerViewHeader(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_PATH_ROW),
            verticalAlignment = Alignment.CenterVertically
        ){
            OpenFullPath(
                path = currentPath,
                modifier = Modifier
                    .fillMaxWidth(fraction = Constants.WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BACK_FOLDER_BUTTON)
                    .align(alignment = Alignment.CenterVertically)
            )

            BackFolderInFolderExplorer(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        if(!currentPath.equals(basePathUriPermited) ) {
                            currentPath = currentPath.substringBeforeLast(Constants.STR_SLASH)
                            mutableSelectUriStr = mutableSelectUriStr.substringBeforeLast(Constants.GLOBAL_STR_URI_FOLDER_LEVEL)
                            foldersAndFiles = getFolders(path = currentPath).toMutableList() + getFiles(context = context, uri = Uri.parse(mutableSelectUriStr) )
                        }
                    }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_MAIN_ROW)
                .padding(vertical = Constants.DP_SIZE_ROW_FOLDERS_PADDING.dp)
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(foldersAndFiles.size){
                    it ->
                    if(foldersAndFiles[it]!!.contains(Constants.GLOBAL_STR_DOT + Constants.GLOBAL_STR_DATABASE_EXTENSION)) {
                        OpenSelectedFile(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = Constants.DP_SIZE_ROW_FILES.dp)
                                .padding(vertical = Constants.DP_SIZE_ROW_FILES_PADDING.dp)
                                .border(
                                    width = if (selectedIndexFile == it) Constants.DP_WIDTH_SELECT_BORDER.dp else Constants.DP_WIDTH_DESELECT_BORDER.dp,
                                    color = if (selectedIndexFile == it) Color.Black else Color.Transparent,
                                    shape = RoundedCornerShape(Constants.DP_ROUNDED_SELECT_BORDER.dp)
                                )
                                .clickable {
                                    selectedIndexFile = it
                                    fileName = foldersAndFiles[it]!!.replace(
                                        Constants.GLOBAL_STR_DOT + Constants.GLOBAL_STR_DATABASE_EXTENSION, ""
                                    )
                                },
                            folderName = foldersAndFiles[it]!!
                        )
                    } else {
                        OpenSelectedFolder(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = Constants.DP_SIZE_ROW_FILES.dp)
                                .padding(vertical = Constants.DP_SIZE_ROW_FILES_PADDING.dp)
                                .clickable {
                                    currentPath += Constants.STR_SLASH + foldersAndFiles[it]
                                    mutableSelectUriStr += Constants.GLOBAL_STR_URI_FOLDER_LEVEL + foldersAndFiles[it]
                                    foldersAndFiles = getFolders(currentPath) + getFiles(context = context, uri = Uri.parse(mutableSelectUriStr ) )
                                    selectedIndexFile = Constants.GLOBAL_NEGATIVE_NUMBER // Se reinicia el valor para que no se seleccione ningun archivo
                                },
                            folderName = foldersAndFiles[it]!!
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_MAIN_ROWS)
        ){
            OpenButtonCancel(
                modifier = Modifier
                    .weight(weight = Constants.WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BUTTONS)
                    .fillMaxHeight(),
                onClick = navigateToLogin/*{
                    navigationController.navigate(route = Routes.ScreenLogin.route)
                }*/
            )
            Spacer(
                modifier = Modifier
                    .weight(weight = Constants.WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BUTTONS_SPACER)
            )
            OpenButtonSelect(
                modifier = Modifier
                    .weight(weight = Constants.WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BUTTONS)
                    .fillMaxHeight(),
                onClick = {
                    if(selectedIndexFile != Constants.GLOBAL_NEGATIVE_NUMBER) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val completePath: String = currentPath + Constants.STR_SLASH + fileName
                            SettingsDataStore.saveOneDatabasePath(
                                context = context,
                                databasePath = completePath
                            )
                        }
                        navigateToLogin()
                    }
                }
            )
        }
    }
}

/**
 * Encabezado de la pantalla
 * @param Modificador para configurar el componente
 */
@Composable
fun OpenFileExplorerViewHeader(modifier: Modifier){
    Text(
        modifier = modifier,
        text = Constants.HEADER_OPEN_FILE_EXPLORER,
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
fun OpenFullPath(path: String, modifier: Modifier){
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
fun OpenSelectedFile(modifier: Modifier, folderName: String){
    val icon = painterResource(R.drawable.document_medicine_svgrepo_com)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.fillMaxHeight(),
            painter = icon,
            contentDescription = Constants.DESCRIPTION_FILE_SELECTED
        )
        Spacer(
            modifier = Modifier
                .width(width = Constants.DP_SIZE_ROW_FILES_SPACER.dp)
        )
        Text(
            text = folderName
        )
    }
}

/**
 * Componente que muestra el nombre de la carpeta que podria seleccionarse
 * @param modifier Para poder configurar el Row de este componente
 * @param folderName Nombre de la carpeta
 */
@Composable
fun OpenSelectedFolder(modifier: Modifier, folderName: String){
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
                .width(width = 15.dp)
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
fun OpenButtonSelect(modifier: Modifier, onClick: () -> Unit){
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp)
    ) {
        Text(
            text = Constants.TEXT_BUTTON_SELECT_FILE,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Boton que al presionarlo cancelara la accion
 * @param modifier Para configurar el componente
 * @param onClick Metodo que se ejecutara al presionar el boton
 */
@Composable
fun OpenButtonCancel(modifier: Modifier, onClick: () -> Unit){
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
fun BackFolderInFolderExplorer(modifier: Modifier){
    val icon = painterResource(R.drawable.undo_left_square_svgrepo_com)
    Icon(
        painter = icon,
        contentDescription = Constants.DESCRIPTION_ICON_BACK_FOLDER,
        modifier = modifier
    )
}

/*@Composable
@Preview(
    showBackground = true
)
fun OpenFileExplorerPreview(){
    val navigationController = rememberNavController()
    OpenFileExplorerView(
        modifier = Modifier
            .fillMaxSize(),
        navigationController = navigationController
    )
}*/


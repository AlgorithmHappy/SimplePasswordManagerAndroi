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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

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
    Log.d("OpenFileExplorer", "selectedFolderString: $selectedFolderString")
    val context: Context = LocalContext.current
    var currentPath by rememberSaveable { mutableStateOf(value = Environment.getExternalStorageDirectory().absolutePath) }
    var currentColor by rememberSaveable { mutableStateOf(value = Color.White.toArgb() ) }
    var selectedIndexFile by rememberSaveable { mutableStateOf(value = 0) }
    val color = Color(currentColor)
    //var selectedFolderUri by rememberSaveable { mutableStateOf(value = Uri.EMPTY) }
    //var selectedFolderUri = Uri.EMPTY
    //var selectedFolderUri by remember { mutableStateOf<Uri?>(value = null) }
    /*val folderPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        uri?.let {
            it ->
            selectedFolderUri = it
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }*/
    var foldersAndFiles by rememberSaveable { mutableStateOf(value = getFolders() + getFiles(context = context, uri = Uri.parse(selectedFolderString) ) ) }

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
                        if(!currentPath.equals(Environment.getExternalStorageDirectory().absolutePath) ) {
                            currentPath = currentPath.substringBeforeLast(Constants.STR_SLASH)
                            foldersAndFiles = getFolders(path = currentPath).toMutableList() + getFiles(context = context, uri = Uri.parse(selectedFolderString) )
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
                                .background(color)
                                .clickable {
                                    // Guardar el color actual antes de cambiarlo
                                    //previousColor = currentColor
                                    // Cambiar el color
                                    //currentColor = if (currentColor == Color.White) Color.Blue else previousColor
                                    currentColor = if (color == Color.White) Color.Cyan.toArgb() else Color.White.toArgb()
                                    selectedIndexFile = it
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
                                    foldersAndFiles = getFolders(currentPath) + getFiles(context = context, uri = Uri.parse(selectedFolderString ) )
                                },
                            folderName = foldersAndFiles[it]!!
                        )
                    }
                }
                /*repeat(5){
                    OpenSelectedFile(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = Constants.DP_SIZE_ROW_FILES.dp)
                            .padding(vertical = Constants.DP_SIZE_ROW_FILES_PADDING.dp),
                        folderName = "Archivo"
                    )
                }
                repeat(5){
                    OpenSelectedFolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = Constants.DP_SIZE_ROW_FILES.dp)
                            .padding(vertical = Constants.DP_SIZE_ROW_FILES_PADDING.dp),
                        folderName = "Carpeta"
                    )
                }*/
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
                    //folderPickerLauncher.launch(input = null)
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


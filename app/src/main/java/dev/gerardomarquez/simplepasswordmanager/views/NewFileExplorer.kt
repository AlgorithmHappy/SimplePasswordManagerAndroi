package dev.gerardomarquez.simplepasswordmanager.views

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.navigations.Routes
import dev.gerardomarquez.simplepasswordmanager.utils.Constants

/**
 * Metodo principal que ordena todos los elementos y variables que contendra la pantalla
 * NewFileExplorer, que sera la pantalla que enlista todas las carpetas para elegir una y ahi
 * guardar la nueva base de datos que almacenara las contraseñas
 * @param modifier Modificador que contendra el padding y el maximo de pantalla de quien lo mande
 * a llamar
 * @param navigationController Objeto que gestiona la navegacion entre pantallas de la aplicacion
 */
@Composable
fun NewFileExplorerView(modifier: Modifier, navigationController: NavHostController){
    val scrollState = rememberScrollState()
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
                path = "C:/miPrimerNivel/MiSegundoNivel/MiTercerNivel", // Cambiar por el path correcto
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterVertically)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_MAIN_ROW)
                .padding(vertical = Constants.DP_SIZE_ROW_FILES_PADDING.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                repeat(10){
                    NewSelectedFolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = Constants.DP_SIZE_ROW_FOLDERS.dp)
                            .padding(vertical = Constants.DP_SIZE_ROW_FOLDERS_PADDING.dp),
                        folderName = "Prueba"
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
                onClick = {
                    navigationController.navigate(route = Routes.ScreenLogin.route)
                }
            )
            Spacer(
                modifier = Modifier
                    .weight(weight = Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS_SPACER)
            )
            NewButtonSelect(
                modifier = Modifier
                    .weight(weight = Constants.WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS)
                    .fillMaxHeight(),
                onClick = {}
            )
        }
    }
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

@Composable
@Preview(
    showBackground = true
)
fun NewFileExplorerPreview(){
    val navigationController = rememberNavController()
    NewFileExplorerView(
        modifier = Modifier
            .fillMaxSize(),
        navigationController = navigationController
    )
}


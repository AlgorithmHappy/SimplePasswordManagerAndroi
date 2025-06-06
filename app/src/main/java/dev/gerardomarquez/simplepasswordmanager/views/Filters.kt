package dev.gerardomarquez.simplepasswordmanager.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModel
import dev.gerardomarquez.simplepasswordmanager.utils.Constants

/**
 * Metodo principal que ordena todos los elementos y variables que contendra la pantalla "Filters"
 * @param modifier Modificador que contendra el padding y el maximo de pantalla de quien lo mande
 * a llamar
 * @param viewModel ViewModel para los datos de esta vista
 * @param navigateToMain Metodo para regresar a la vista main
 */
@Composable
fun Filters(
    modifier: Modifier,
    viewModel: PasswordsInformationsViewModel,
    navigateToMain: () -> Unit
){
    val context = LocalContext.current
    var dataTitle: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_TITLE,
        selected = viewModel.stateFilters.title,
        onCheckedChange = {
            viewModel.changeFilterTitle(title = it)
        }
    )
    var dataUser: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_USER,
        selected = viewModel.stateFilters.user,
        onCheckedChange = {
            viewModel.changeFilterUser(user = it)
        }
    )
    var dataComments: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_COMMENTS,
        selected = viewModel.stateFilters.notes,
        onCheckedChange = {
            viewModel.changeFilterNotes(notes = it)
        }
    )
    var dataUrl: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_URL,
        selected = viewModel.stateFilters.url,
        onCheckedChange = {
            viewModel.changeFilterUrl(url = it)
        }
    )
    var dataEmail: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_EMAIL,
        selected = viewModel.stateFilters.email,
        onCheckedChange = {
            viewModel.changeFilterEmail(email = it)
        }
    )
    var dataPhone: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_PHONE,
        selected = viewModel.stateFilters.phone,
        onCheckedChange = {
            viewModel.changeFilterNumber(number = it)
        }
    )
    var confirmationDialog by rememberSaveable { mutableStateOf(value = false)}
    var textDialog by rememberSaveable { mutableStateOf(value = "") }

    Column(
        modifier = modifier // Este modificador sera el que se pasa como argumento, se tendra que modificar mas adelante
            .fillMaxSize()
            .padding(
                horizontal = Constants.DP_PADDING.dp,
                vertical = Constants.DP_PADDING.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        HeaderFilters()
        CheckFilters(
            checkInformation = dataTitle
        )
        CheckFilters(
            checkInformation = dataUser
        )
        CheckFilters(
            checkInformation = dataComments
        )
        CheckFilters(
            checkInformation = dataUrl
        )
        CheckFilters(
            checkInformation = dataEmail
        )
        CheckFilters(
            checkInformation = dataPhone
        )
        Spacer(
            Modifier.fillMaxWidth().height(height = Constants.DP_SACER_FILTERS.dp)
        )
        FiltersInstructions()
        Spacer(
            Modifier.fillMaxWidth().height(height = Constants.DP_SACER_FILTERS.dp)
        )
        ButtonSaveFilters(
            onClick = {
                if(viewModel.stateFilters.user || viewModel.stateFilters.notes ||
                    viewModel.stateFilters.url || viewModel.stateFilters.email ||
                    viewModel.stateFilters.phone || viewModel.stateFilters.title){

                    viewModel.saveAllFilters(context = context)
                    textDialog = Constants.TEXT_ALERT_DIALOG_FILTERS_OK

                } else{
                    textDialog = Constants.TEXT_ALERT_DIALOG_FILTERS_NO_OK
                }
                confirmationDialog = true
            }
        )
    }

    DialogFilters(
        show = confirmationDialog,
        text = textDialog,
        onDismissRequest = {
            confirmationDialog = false
            if(textDialog == Constants.TEXT_ALERT_DIALOG_FILTERS_OK) navigateToMain()
        }
    )
}

/**
 * Encabezado de la pantalla
 */
@Composable
fun HeaderFilters(){
    Text(
        text = Constants.HEADER_FILTERS,
        fontSize = Constants.SIZE_TEXT_TITLE.sp,
        textAlign = TextAlign.Center
    )
}

/**
 * Elemento que contiene todos los filtros con los que se puede realizar la busqueda
 * @param checkInformation Datos empaquetados en una clase que necesita este metodo
 */
@Composable
fun CheckFilters(checkInformation: CheckDataFilters){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = checkInformation.selected,
            onCheckedChange = checkInformation.onCheckedChange
        )
        Text(
            text = checkInformation.title
        )
    }
}

/**
 * Instrucciones para que el usuario pueda manejar bien esta pantalla
 */
@Composable
fun FiltersInstructions(){
    Text(
        text = Constants.TEXT_FILTERS_INSTRUCTIONS
    )
}

/**
 * Boton que guarda los filtros en el archivo de propiedades
 */
@Composable
fun ButtonSaveFilters(onClick: () -> Unit){
    OutlinedButton(
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
        onClick = onClick
    ) {
        Text(text = Constants.TEXT_BUTTON_SAVE_FILTERS)
    }
}

/**
 * Dialogo que confirma qe se guardaron correctamente las configuraciones de los filtros
 * @param show Con esta variable se define si se mostrara o no el dialogo
 * @param onDismissRequest Con este metodo se cambia el valor de la variable show para ocultar el
 * dialogo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogFilters(show: Boolean, text: String, onDismissRequest: () -> Unit){
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
                    //text = Constants.TEXT_ALERT_DIALOG_FILTERS_OK,
                    text = text,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Clase para los checks de la pantalla con la informacion necesaria
 */
data class CheckDataFilters(
    val title: String, // Titulo del check
    var selected: Boolean = false, // Si el check esta seleccionado o no
    var onCheckedChange: (Boolean) -> Unit // Metodo para cambiar la variable "selected"
)

/*@Composable
@Preview(
    showBackground = true
)
fun FiltersPreview(){
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Routes.ScreenFilters.route
    ){
        composable(Routes.ScreenFilters.route){
            Filters(
                modifier = Modifier.fillMaxSize(),
                navigationController = navigationController
            )
        }
        composable(Routes.ScreenMain.route){
            Main(
                modifier = Modifier.fillMaxSize(),
                navigationController = navigationController
            )
        }
    }
}*/
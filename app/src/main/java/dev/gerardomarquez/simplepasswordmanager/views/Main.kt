package dev.gerardomarquez.simplepasswordmanager.views

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import dev.gerardomarquez.simplepasswordmanager.R
import dev.gerardomarquez.simplepasswordmanager.utils.Constants

/**
 * Metodo principal que ordena todos los elementos y variables que contendra la pantalla MAIN, que
 * sera la pantalla que enlista todas las contraseñas guardadas
 * @param modifier Modificador que contendra el padding y el maximo de pantalla de quien lo mande
 * a llamar
 */
@Composable
fun Main(modifier: Modifier){
    val scrollState = rememberScrollState()
    var textSearch by rememberSaveable { mutableStateOf( value = String() ) }
    var information: DataPassword = DataPassword(
        title = "Titulo",
        user =  "usuario",
        password = "password",
        url = "URL",
        email = "email",
        phone = "phone",
        comments = "comentarios",
        secretCode = "token codigo secreto000000000000"
    )

    Column(
        modifier = Modifier // Este modificador sera el que se pasa como argumento, se tendra que modificar mas adelante
            .fillMaxSize()
            .padding(
                horizontal = Constants.DP_PADDING.dp,
                vertical = Constants.DP_PADDING.dp
            ),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_MAIN_SOME_ROWS),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            HeaderMain()
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_MAIN_SOME_ROWS),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            SearchBarMain(
                modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_SEARCH_BAR_MAIN),
                placeHolder = Constants.DESCRIPTION_DATA_INPUT_SEARCH_BAR,
                dataInput = textSearch,
                onDataInputChange = {
                    textSearch = it
                }
            )
            Spacer(
                modifier = Modifier.weight(weight = Constants.WEIGHT_LAYOUT_MAIN_FILTER)
            )
            IconFilter(
                modifier = Modifier
                    .weight(Constants.WEIGHT_LAYOUT_ICON_FILTER)
                    .clickable {

                    }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Constants.WEIGHT_LAYOUT_MAIN_MIDLE_ROW)
                .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(Constants.WEIGHT_LAYOUT_FULL)
                    .verticalScroll(scrollState)
            ){
                repeat(15){ // Aqui se tendra que modificar para aceptar todos los elementos
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Constants.DP_HEIGHT_PASSWORDS_DROPDOWN.dp)
                    ){
                        InformationPasswordDropDown(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = Constants.DP_PADDING_PASSWORDS_DROPDOWNS_MENUS.dp),
                            information = information
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(Constants.WEIGHT_LAYOUT_MAIN_SOME_ROWS)
        ){
            ButtonInsert(
                modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_MAIN_BUTTONS).fillMaxHeight(),
                onClick = {

                }
            )
            Spacer(
                modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_A_TENTH)
            )
            ButtonSaveFile(
                modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_MAIN_BUTTONS).fillMaxHeight(),
                onClick = {

                }
            )
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
    information: DataPassword
) {
    var expanded by remember { mutableStateOf(false) }

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
                text = information.title,
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
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = information.user,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
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
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = information.password,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
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
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = information.secretCode,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
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
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = information.comments,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
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
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = information.url,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
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
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = information.email,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
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
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = information.phone,
                        modifier = Modifier.weight(Constants.WEIGHT_LAYOUT_INFORMATION_DROPDOWN),
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(0.375f),
                        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                        onClick = {

                        }
                    ){
                        Text(
                            text = Constants.TEXT_BUTTON_DELETE
                        )
                    }
                    Spacer(
                        modifier = Modifier.weight(0.15f)
                    )
                    Button(
                        modifier = Modifier.weight(0.375f),
                        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
                        onClick = {

                        }
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
 * Clase para los dropdown que contendra toda la informacion de las cuentas guardadas
 */
data class DataPassword(
    var title: String, // Titulo del check
    var user: String, // Si el check esta seleccionado o no
    var password: String,
    var secretCode: String, // Metodo para cambiar la variable "selected"
    var comments: String, // Titulo del check
    var url: String, // Titulo del check
    var email: String, // Titulo del check
    var phone: String, // Titulo del check
)

@Composable
@Preview(
    showBackground = true
)
fun MainPreview(){
    Main(modifier = Modifier.fillMaxSize())
}
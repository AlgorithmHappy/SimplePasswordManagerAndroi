package dev.gerardomarquez.simplepasswordmanager.views

import android.R
import android.util.Log
import android.widget.CheckBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gerardomarquez.simplepasswordmanager.utils.Constants

@Composable
fun Filters(modifier: Modifier){
    var titleSelected by rememberSaveable { mutableStateOf(value = false) }
    var dataTitle: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_TITLE,
        selected = titleSelected,
        onCheckedChange = {
            titleSelected = it
        }
    )
    var userSelected by rememberSaveable { mutableStateOf(value = false) }
    var dataUser: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_USER,
        selected = userSelected,
        onCheckedChange = {
            userSelected = it
        }
    )
    var commentsSelected by rememberSaveable { mutableStateOf(value = false) }
    var dataComments: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_COMMENTS,
        selected = commentsSelected,
        onCheckedChange = {
            commentsSelected = it
        }
    )
    var urlSelected by rememberSaveable { mutableStateOf(value = false) }
    var dataUrl: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_URL,
        selected = urlSelected,
        onCheckedChange = {
            urlSelected = it
        }
    )
    var emailSelected by rememberSaveable { mutableStateOf(value = false) }
    var dataEmail: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_EMAIL,
        selected = emailSelected,
        onCheckedChange = {
            emailSelected = it
        }
    )
    var phoneSelected by rememberSaveable { mutableStateOf(value = false) }
    var dataPhone: CheckDataFilters = CheckDataFilters(
        title = Constants.TEXT_CHECK_PHONE,
        selected = phoneSelected,
        onCheckedChange = {
            phoneSelected = it
        }
    )


    Column(
        modifier = Modifier // Este modificador sera el que se pasa como argumento, se tendra que modificar mas adelante
            .fillMaxSize()
            .padding(horizontal = Constants.DP_PADDING.dp, vertical = Constants.DP_PADDING.dp),
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
        FiltersInstructions()
        ButtonSaveFilters()
    }
}

@Composable
fun HeaderFilters(){
    Text(
        text = Constants.HEADER_FILTERS,
        fontSize = Constants.SIZE_TEXT_TITLE.sp,
        textAlign = TextAlign.Center
    )
}

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

@Composable
fun FiltersInstructions(){
    Text(
        text = Constants.TEXT_FILTERS_INSTRUCTIONS
    )
}

@Composable
fun ButtonSaveFilters(){
    OutlinedButton(
        shape = RoundedCornerShape(Constants.DP_ROUNDED_BUTTON.dp),
        onClick = {
            Log.i("logica:", "Aqui se hara la logica de guardar los datos en SQLite")
        }
    ) {
        Text(text = Constants.TEXT_BUTTON_SAVE_FILTERS)
    }
}

data class CheckDataFilters(
    val title: String,
    var selected: Boolean = false,
    var onCheckedChange: (Boolean) -> Unit
)

@Composable
@Preview(
    showBackground = true
)
fun FiltersPreview(){
    Filters(modifier = Modifier.fillMaxSize())
}
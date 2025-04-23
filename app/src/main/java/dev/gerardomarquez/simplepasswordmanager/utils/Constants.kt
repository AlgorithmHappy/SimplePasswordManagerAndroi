package dev.gerardomarquez.simplepasswordmanager.utils

import android.util.Base64

/**
 * Clase que contendra las variables constantes que se usaran para todo el proyecto
 */
class Constants {
    companion object {

        /**
         * Encabezados de todas las vistas que se utilizaran
         */
        val HEADER_LOGIN: String = "Simple password manager";
        val HEADER_DATA_INSERT: String = "Insertar datos";
        val HEADER_DATA_UPDATE: String = "Actualizar datos";
        val HEADER_FILTERS: String = "Configuración de filtros de busqueda";
        val HEADER_NEW_FILE_EXPLORER: String = "Selecciona la carpeta de guardado";
        val HEADER_OPEN_FILE_EXPLORER: String = "Selecciona un archivo para visualizarlo";
        val HEADER_SETTINGS: String = "Configuraciones de la aplicacion";

        /**
         * Descripciones de imageners
         */
        val IMAGE_DESCRIPTION: String = "Logo de la aplicacion";

        /**
         * Para asignar pesos en un layout
         */
        val WEIGHT_LAYOUT_A_TENTH: Float = 0.1f;
        val WEIGHT_LAYOUT_NINE_TENTHS: Float = 0.9f;
        val WEIGHT_LAYOUT_THREE_TENTHS: Float = 0.3f;
        val WEIGHT_LAYOUT_DROP_DOWN_BUTTON: Float = 0.13f;
        val WEIGHT_LAYOUT_DROP_DOWN: Float = 0.87f;
        val WEIGHT_LAYOUT_WIDTH_LOGIN_BUTTON: Float = 0.4f
        val WEIGHT_LAYOUT_HEIGTH_LOGIN_BUTTON: Float = 0.7f
        val WEIGHT_LAYOUT_HEIGTH_DROPDOWN_ARROW: Float = 0.4f
        val WEIGHT_LAYOUT_NEW_BUTTON: Float = 0.06f
        val WEIGHT_LAYOUT_MAIN_INSERT: Float = 0.7f
        val WEIGHT_LAYOUT_MAIN_UPDATE: Float = 0.7f
        val WEIGHT_LAYOUT_A_MEDIUM: Float = 0.5f
        val WEIGHT_LAYOUT_ONE_EIGHTH: Float = 0.125f
        val WEIGHT_LAYOUT_DIALOGS_HEIGHT: Float = 0.25f
        val WEIGHT_LAYOUT_DIALOGS_WIDTH: Float = 0.95f
        val WEIGHT_LAYOUT_DIALOGS_NEW_FILE_HEIGHT: Float = 0.5f
        val WEIGHT_LAYOUT_DIALOGS_NEW_FILE_WIDTH: Float = 0.95f
        val WEIGHT_LAYOUT_PASSWORD_DIALOG_HEIGHT: Float = 0.70f
        val WEIGHT_LAYOUT_PASSWORD_DIALOG_WIDTH: Float = 0.95f
        val WEIGHT_LAYOUT_MAIN_FILTER: Float = 0.03f
        val WEIGHT_LAYOUT_SEARCH_BAR_MAIN: Float = 0.84f
        val WEIGHT_LAYOUT_ICON_FILTER: Float = 0.13f
        val WEIGHT_LAYOUT_MAIN_SOME_ROWS: Float = 0.08f
        val WEIGHT_LAYOUT_MAIN_MIDLE_ROW: Float = 0.8f
        val WEIGHT_LAYOUT_MAIN_BUTTONS: Float = 0.45f
        val WEIGHT_LAYOUT_FULL: Float = 1f
        val WEIGHT_LAYOUT_INFORMATION_DROPDOWN: Float = 0.45f
        val WEIGHT_LAYOUT_BUTTONS_DROPDOWN: Float = 0.375f
        val WEIGHT_LAYOUT_BUTTONS_SPACER_DROPDOWN: Float = 0.15f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_MAIN_ROWS: Float = 0.085f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_MAIN_ROW: Float = 0.745f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_PATH_ROW: Float = 0.085f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS: Float = 0.45f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS_SPACER: Float = 0.1f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BACK_FOLDER_BUTTON: Float = 0.85f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_MAIN_ROWS: Float = 0.085f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_MAIN_ROW: Float = 0.745f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_PATH_ROW: Float = 0.085f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BUTTONS: Float = 0.45f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BUTTONS_SPACER: Float = 0.1f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BACK_FOLDER_BUTTON: Float = 0.85f

        /**
         * Descripcion de los widgets para que le entienda el usuario
         */
        val DESCRIPTION_DROPDOWN_LOGIN: String = "Examinar";
        val DESCRIPTION_ICON_DROPDOWN_LOGIN: String = "Desplegar menú";
        val DESCRIPTION_ICON_OPEN: String = "Abrir explorador de archivos"
        val DESCRIPTION_PASSWORD: String = "Contraseña";
        val DESCRIPTION_PASSWORD_VISIBILITY: String = "Boton para ocultar o mostrar contraseña/token"
        val DESCRIPTION_ICON_SEARCH: String = "Lupa de busqueda"
        val DESCRIPTION_ICON_FILTER: String = "Filtro de busqueda"
        val DESCRIPTION_FOLDER_SELECTED: String = "Seleccionar folder"
        val DESCRIPTION_FILE_SELECTED: String = "Seleccionar archivo"
        val DESCRIPTION_ICON_NEW: String = "Abrir explorador de archivos"
        val DESCRIPTION_ICON_SETTINGS: String = "Abrir configuracion de aplicacion"
        val DESCRIPTION_ICON_REFRESH: String = "Generar contraseña"
        val DESCRIPTION_ICON_REFRESH_SALT: String = "Generar salt"
        val DESCRIPTION_ICON_BACK_FOLDER: String = "Regresar a la carpeta anterior"
        val DESCRIPTION_DATA_INPUT_TITLE: String = "Ej. Cuenta de Facebook"
        val DESCRIPTION_DATA_INPUT_USER: String = "Ej. JuanP096"
        val DESCRIPTION_DATA_INPUT_PASSWORD: String = "Ej. agesIU1705@"
        val DESCRIPTION_DATA_INPUT_TOKEN: String = "Ej. fa0195ba..."
        val DESCRIPTION_DATA_INPUT_COMMENTS: String = "Ej. Mi facebook principal"
        val DESCRIPTION_DATA_INPUT_URL: String = "Ej. www.facebook.com"
        val DESCRIPTION_DATA_INPUT_EMAIL_RECOVERY: String = "Ej. micorreo@gmail.com"
        val DESCRIPTION_DATA_INPUT_PHONE_NUMBER: String = "Ej. 5520306688"
        val DESCRIPTION_DATA_INPUT_SEARCH_BAR: String = "Ej. tiktok"

        /**
         * Variables globales
         */
        val GLOBAL_START_INDEX: Int = 0;
        val GLOBAL_NEGATIVE_NUMBER: Int = -1;
        val GLOBAL_SELECCIONAR: String = "Selecciona una opción"
        val STR_SLASH: String = "/"
        val STR_CORCHETES: String = "[]"
        val GLOBAL_STR_DATABASE_EXTENSION: String = "db"
        val GLOBAL_STR_DOT: String = "."
        val GLOBAL_STR_URI_TO_PATH: String = "/tree/primary:"
        val GLOBAL_STR_URI_FOLDER_LEVEL: String = "%2F"
        val GLOBAL_STR_LEFT_BRACKET: String = "["
        val GLOBAL_STR_RIGHT_BRACKET: String = "]"
        val GLOBAL_STR_DOUBLE_QUOTATION_MARKS: String = "\""
        val GLOBAL_INT_ONE: Int = 1

        /**
         * Tamaños de textos
         */
        val SIZE_TEXT_TITLE: Int = 24;
        val SIZE_TEXT_PLACE_HOLDER: Int = 10;
        val SIZE_TEXT_TITLE_DROPDOWN: Int = 16;
        val SIZE_TEXT_VALUES_DROPDOWN: Int = 14;
        val SIZE_TEXT_PATH: Int = 10;

        /**
         * Tamaños de spaces
         */
        val SIZE_SPACE_WEIGHT_LAST_LOGIN: Float = 0.14F;

        /**
         * Textos
         */
        val TEXT_LOGIN: String = "Ingresar";
        val TEXT_BUTTON_INSERTAR: String = "Insertar";
        val TEXT_BUTTON_UPDATE: String = "Actualizar";
        val TEXT_BUTTON_DELETE: String = "Eliminar";
        val TEXT_BUTTON_CANCEL: String = "Cancelar";
        val TEXT_PASSWORD_GENERATOR: String = "Crear clave";
        val TEXT_BUTTON_SAVE_FILE: String = "Guardar archivo";
        val TEXT_BUTTON_NEW_INSERT: String = "Insertar nuevo";
        val TEXT_BUTTON_SAVE_FILTERS: String = "Guardar filtros";
        val TEXT_BUTTON_SAVE_SALT: String = "Guardar";
        val TEXT_BUTTON_SELECT: String = "Seleccionar"
        val TEXT_BUTTON_SELECT_FILE: String = "Seleccionar archivo"
        val TEXT_INSERT_VIEW_INSTRUCTIONS: String = "Los campos marcados con \"*\" son obligatorios y el campo titulo debe ser unico"
        val TEXT_ALERT_DIALOG_INSERT_OK: String = "Se ha insertado correctamente el registro, para mantener los cambios favor de presionar el boton \"Guardar archivo\""
        val TEXT_ALERT_DIALOG_PASSWORD_GENERATOR: String = "Generar contraseña aleatoria"
        val TEXT_ALERT_DIALOG_PASSWORD_GENERATOR_SIZE: String = "Seleccione el tamaño de la contraseña:"
        val TEXT_ALERT_DIALOG_UPDATE_OK: String = "Se ha actualizado correctamente el registro, para mantener los cambios favor de presionar el boton \"Guardar archivo\""
        val TEXT_ALERT_DIALOG_FILTERS_OK: String = "Se ha guardado correctamente la configuracion de filtros"
        val TEXT_ALERT_DIALOG_FILTERS_NO_OK: String = "'NO' Se ha guardado la configuracion de filtros debido a que no se selecciono ningun filtro"
        val TEXT_ALERT_DIALOG_MAIN_SAVE: String = "Se ha guardado correctamente los registros en el archivo"
        val TEXT_ALERT_DIALOG_MAIN_DELATE: String = "¿Seguro que quieres eliminar el archivo?"
        val TEXT_ALERT_DIALOG_NEW_FILE: String = "Ingrese el nombre del archivo:"
        val TEXT_ERROR_PASSWORD: String = "La contraseña es incorrecta"
        val TEXT_ALERT_DIALOG_NEW_FILE_PASSWORD: String = "Ingrese la contraseña del archivo:"
        val TEXT_CHECK_TITLE: String = "Titulo";
        val TEXT_CHECK_USER: String = "Usuario";
        val TEXT_CHECK_COMMENTS: String = "Comentarios";
        val TEXT_CHECK_URL: String = "Pagina WEB";
        val TEXT_CHECK_EMAIL: String = "Correo de recuperacion";
        val TEXT_CHECK_PHONE: String = "Telefono de recuperacion";
        val TEXT_FILTERS_INSTRUCTIONS: String = "*Al menos un filtro deberia estar activo";
        val TEXT_MAIN: String = "Lista de contraseñas"
        val TEXT_BUTTON_OK: String = "Aceptar"
        val TXT_TITLE: String = "*Titulo:"
        val TXT_USER: String = "*Usuario:"
        val TXT_PASSWORD: String = "*Contraseña:"
        val TXT_SECRED_CODE_TOKEN: String = "Codigo secreto ó Token:"
        val TXT_COMMENTS: String = "Comentarios:"
        val TXT_URL: String = "Pagina WEB:"
        val TXT_EMAIL: String = "Correo de recuperación:"
        val TXT_PHONE_NUMBER: String = "Cel. de recuperación:"
        val TXT_USER_DROPDOWN: String = "Usuario:"
        val TXT_PASSWORD_DROPDOWN: String = "Contraseña:"
        val TXT_ERROR_PASSWORD_TITLE_CONSTRAINT: String = "El titulo no puede tener más de 20 caracteres"
        val TXT_ERROR_USERNAME_CONSTRAINT: String = "El nombre de usuario no puede tener más de 20 caracteres"
        val TXT_ERROR_PASSWORD_CONSTRAINT: String = "El password no puede tener más de 18 caracteres"
        val TXT_ERROR_TOKEN_CONSTRAINT: String = "El token o codigo secreto no puede tener más de 257 caracteres"
        val TXT_ERROR_EMAIL_CONSTRAINT: String = "El correo electronico no puede tener más de 30 caracteres"
        val TXT_ERROR_PHONE_CONSTRAINT: String = "El número de telefono debe tener máximo 12 dígitos y ser positivo"
        val TXT_ERROR_URL_CONSTRAINT: String = "La pagina web no puede tener más de 100 caracteres"
        val TXT_ERROR_NOTES_CONSTRAINT: String = "Los comentarios no puede tener más de 500 caracteres"
        val TXT_COPY_USER: String = "Copiar usuario"
        val TXT_COPY_PASSWORD: String = "Copiar contraseña"
        val TXT_COPY_TOKEN: String = "Copiar token"
        val TXT_ERROR_CHRACTER_TYPE: String = "At least one character type must be selected."
        val TXT_LOWER_CASE: String = "abcdefghijklmnopqrstuvwxyz"
        val TXT_UPPER_CASE: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val TXT_NUMBERS: String = "0123456789"
        val TXT_SPECIAL_CHARACTERS: String = "!@#$%&*()-_=+[]{};:,.<>?/"
        val TXT_LOWER_CASE_CHEK: String = "Incluir minusculas"
        val TXT_UPPER_CASE_CHEK: String = "Incluir mayusculas"
        val TXT_NUMBERS_CHEK: String = "Incluir numeros"
        val TXT_SPECIAL_CHARACTERS_CHEK: String = "Incluir caracteres especiales"
        val TXT_USE_PASSWORD_GENERATED: String = "Usar"
        val TXT_COPY_PASSWORD_GENERATED: String = "Copiar"
        val TXT_SETTINGS_SALT: String = "Cambiar salt:"
        val TXT_SETTINGS_SALT_SELECT: String = "Elegir uno creado previamente:"
        val TXT_SETTINGS_SALT_WARNING: String = "*El \"salt\" es como el candado de tu contraseña, si se cambia el salt la contraseña se vuelve irreconocible. Guardar el salt junto con tu contraseña"

        /**
         * Textos de propiedades de configuracion
         */
        val SETTINGS_FILE_NAME: String = "settings_prefs"
        val SETTINGS_LIST_DATABASES: String = "database_list"
        val SETTINGS_FILTERS_TITLE: String = "filters_title"
        val SETTINGS_FILTERS_USER: String = "filters_user"
        val SETTINGS_FILTERS_NOTES: String = "filters_notes"
        val SETTINGS_FILTERS_URL: String = "filters_url"
        val SETTINGS_FILTERS_EMAIL: String = "filters_email"
        val SETTINGS_FILTERS_NUMBER: String = "filters_number"
        val SETTINGS_LIST_SALT: String = "salt_list"

        /**
         * Tamaños de dp's
         */
        val DP_PADDING: Int = 30
        val DP_ROUNDED_BUTTON: Int = 4
        val DP_PADDING_INSERTS_BUTTONS: Int = 10
        val DP_PADDING_DIALOGS: Int = 20
        val DP_ROUNDED_DIALOGS: Int = 4
        val DP_SACER_FILTERS: Int = 15
        val DP_PADDING_PASSWORDS_DROPDOWNS_MENUS: Int = 10
        val DP_HEIGHT_PASSWORDS_DROPDOWN: Int = 70
        val DP_ROUNDED_ROW: Int = 4
        val DP_ROUNDED_SELECT_BORDER: Int = 8
        val DP_WIDTH_SELECT_BORDER: Int = 3
        val DP_WIDTH_DESELECT_BORDER: Int = 0
        val DP_PADDING_INFORMATION_DROPDOWN: Int = 16
        val DP_SIZE_ROW_FOLDERS: Int = 90
        val DP_SIZE_ROW_FOLDERS_PADDING: Int = 10
        val DP_SIZE_ROW_FOLDERS_SPACER: Int = 15
        val DP_SIZE_ROW_FILES: Int = 90
        val DP_SIZE_ROW_FILES_PADDING: Int = 10
        val DP_SIZE_ROW_FILES_SPACER: Int = 15

        /**
         * Consultas a la base de datos
         */
        const val QUERY_SELECT_ALL: String = "SELECT * FROM passwords_informations"
        const val QUERY_SELECT_ONE_BY_ID: String = "SELECT * FROM passwords_informations WHERE id = :id"

        private val B64_SALT: String = "VGVzdFNhbHRAMTIzNA==" // Solo pruebas, se tiene que cambiar por un salt seguro, no se si hacer otra seccion para que el usuario pueda generar uno seguro y aleatorio

        val PATH_TMP_DATABASE: String = "/data/data/dev.gerardomarquez.simplepasswordmanager/databases/tmp_database"
        val SALT_FILE_NAME: String = "salt.txt"
    }
}
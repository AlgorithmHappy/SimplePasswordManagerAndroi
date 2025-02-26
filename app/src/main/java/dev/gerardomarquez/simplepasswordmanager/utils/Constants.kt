package dev.gerardomarquez.simplepasswordmanager.utils

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
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_MAIN_ROWS: Float = 0.09f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_MAIN_ROW: Float = 0.8f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_PATH_ROW: Float = 0.02f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS: Float = 0.45f
        val WEIGHT_LAYOUT_NEW_FILE_EXPLORER_BUTTONS_SPACER: Float = 0.1f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_MAIN_ROWS: Float = 0.09f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_MAIN_ROW: Float = 0.8f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_PATH_ROW: Float = 0.02f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BUTTONS: Float = 0.45f
        val WEIGHT_LAYOUT_OPEN_FILE_EXPLORER_BUTTONS_SPACER: Float = 0.1f

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
        val TEXT_BUTTON_SAVE_FILE: String = "Guardar archivo";
        val TEXT_BUTTON_NEW_INSERT: String = "Insertar nuevo";
        val TEXT_BUTTON_SAVE_FILTERS: String = "Guardar filtros";
        val TEXT_BUTTON_SELECT: String = "Seleccionar"
        val TEXT_BUTTON_SELECT_FILE: String = "Seleccionar archivo"
        val TEXT_INSERT_VIEW_INSTRUCTIONS: String = "Los campos marcados con \"*\" son obligatorios y el campo titulo debe ser unico"
        val TEXT_ALERT_DIALOG_INSERT_OK: String = "Se ha insertado correctamente el registro, para mantener los cambios favor de presionar el boton \"Guardar archivo\""
        val TEXT_ALERT_DIALOG_UPDATE_OK: String = "Se ha actualizado correctamente el registro, para mantener los cambios favor de presionar el boton \"Guardar archivo\""
        val TEXT_ALERT_DIALOG_FILTERS_OK: String = "Se ha guardado correctamente la configuracion de filtros"
        val TEXT_ALERT_DIALOG_MAIN_SAVE: String = "Se ha guardado correctamente los registros en el archivo"
        val TEXT_ALERT_DIALOG_MAIN_DELATE: String = "¿Seguro que quieres eliminar el archivo?"
        val TEXT_CHECK_TITLE: String = "Titulo";
        val TEXT_CHECK_USER: String = "Usuario";
        val TEXT_CHECK_COMMENTS: String = "Comentarios";
        val TEXT_CHECK_URL: String = "Pagina WEB";
        val TEXT_CHECK_EMAIL: String = "Correo de recuperacion";
        val TEXT_CHECK_PHONE: String = "Telefono de recuperacion";
        val TEXT_FILTERS_INSTRUCTIONS: String = "*Al menos un filtro deberia estar activo";
        val TEXT_MAIN: String = "Lista de contraseñas"

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
        val DP_PADDING_INFORMATION_DROPDOWN: Int = 16
        val DP_SIZE_ROW_FOLDERS: Int = 90
        val DP_SIZE_ROW_FOLDERS_PADDING: Int = 10
        val DP_SIZE_ROW_FOLDERS_SPACER: Int = 15
        val DP_SIZE_ROW_FILES: Int = 90
        val DP_SIZE_ROW_FILES_PADDING: Int = 10
        val DP_SIZE_ROW_FILES_SPACER: Int = 15

        /**
         * Texts
         */
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

    }
}
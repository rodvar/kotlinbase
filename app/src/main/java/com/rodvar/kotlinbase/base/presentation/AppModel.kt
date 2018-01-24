package com.rodvar.kotlinbase.base.presentation

import java.io.Serializable

/**
 * Created by rodvar on 13/9/17.
 */
interface AppModel : Serializable {

    val isValid: Boolean

}
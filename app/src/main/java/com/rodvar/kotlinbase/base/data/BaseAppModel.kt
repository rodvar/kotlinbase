package com.rodvar.kotlinbase.base.data

import com.rodvar.kotlinbase.base.presentation.AppModel

/**
 * Created by rodvar on 13/9/17.
 *
 * Base class for every app model
 */
abstract class BaseAppModel : AppModel {

    var status: Status? = null

    /**
     * Default is having 1 object of data
     *
     * @return 1
     */
    fun size(): Int {
        return 1
    }

    override val isValid: Boolean
        get() = if (!this.hasStatus()) false else this.status?.isSuccess!!

    fun hasStatus(): Boolean {
        return this.status != null
    }

}

open class Status {

    var code: String = ""
    var message: String = ""
    val isSuccess: Boolean = false

}

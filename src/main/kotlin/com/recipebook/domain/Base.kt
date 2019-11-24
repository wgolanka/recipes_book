package com.recipebook.domain

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable

abstract class Base : Serializable, AbstractJpaPersistable<Base>() {

    abstract val emailAddress: String
    abstract var phoneNumber: String?
}
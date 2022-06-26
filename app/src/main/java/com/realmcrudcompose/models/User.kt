package com.realmcrudcompose.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class User(
    @PrimaryKey
    var id: String = "",
    var nome: String = ""
) : RealmObject()

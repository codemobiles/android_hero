// To parse the JSON, install Klaxon and do:
//
//   val welcome = Welcome.fromJson(jsonString)

package com.codemobiles.androidhero.models

import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

class ProductAllJson(elements: Collection<ProductAllResponse>) : ArrayList<ProductAllResponse>(elements) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = ProductAllJson(klaxon.parseArray<ProductAllResponse>(json)!!)
    }
}

data class ProductAllResponse (
    val id: Long,
    val name: String,
    val image: String,
    val stock: Long,
    val price: Long,
    val createdAt: String,
    val updatedAt: String
)

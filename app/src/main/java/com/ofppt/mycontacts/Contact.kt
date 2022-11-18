package com.ofppt.mycontacts

import java.util.*


class Contact {
    var name: String? = null
    var number: String? = null

    constructor() {}
    constructor(name: String?, number: String?) {
        this.name = name
        this.number = number
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val contact = o as Contact
        return name == contact.name && number == contact.number
    }

    override fun hashCode(): Int {
        return Objects.hash(name, number)
    }
}

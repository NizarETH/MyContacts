package com.ofppt.mycontacts

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.open_contacts).setOnClickListener(
            {
                getContactListFromLocal()
            }
        )
    }
    private fun getContactListFromLocal() {

        val noDuplicatedList: MutableList<Contact?> = ArrayList<Contact?>()
        Executors.newSingleThreadExecutor().execute {

                val myContacts: MutableList<Contact?> =
                    ArrayList<Contact?>()

                // Get query phone contacts cursor object.
                val readContactsUri =
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                val cursor: Cursor =
                    this@MainActivity.getContentResolver()
                        .query(readContactsUri, null, null, null, null)!!
                if (cursor != null) {
                    cursor.moveToFirst()

                    // Loop in the phone contacts cursor to add each contacts in phoneContactsList.
                    do {
                        // Get contact display name.
                        val displayNameIndex =
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                        if (displayNameIndex != -1) {
                            val userDisplayName = cursor.getString(displayNameIndex)

                            // Get contact phone number.
                            val phoneNumberIndex =
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            var phoneNumber = cursor.getString(phoneNumberIndex)
                            phoneNumber = phoneNumber!!.replace("-", "")
                            phoneNumber = phoneNumber.replace(" ", "")
                            val regex: String = "^[+]?[(]?[0-9]{3}[)]?[-\\\\.]?[0-9]{3}[-\\\\.]?[0-9]{4,6}\$"
                            val pattern = Pattern.compile(regex)
                            val matcher = pattern.matcher(phoneNumber)
                            var contact: Contact? = null
                            if (matcher.matches() && userDisplayName != null && !userDisplayName.isEmpty()
                                && phoneNumber != null && !phoneNumber.isEmpty()
                            ) {
                                contact = Contact(userDisplayName, phoneNumber)
                            }
                            myContacts.add(contact)
                        }
                    } while (cursor.moveToNext())
                }


                Toast.makeText(this@MainActivity, " size "+myContacts.size, Toast.LENGTH_LONG).show()

        }
    }
}
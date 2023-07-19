package uz.gita.imagefiltersapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.displayToast(msg: String?){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.show(){
    this.visibility = View.VISIBLE
}
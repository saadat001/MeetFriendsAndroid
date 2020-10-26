package com.websoftquality.meetfriends.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.request.RequestOptions
import com.websoftquality.meetfriends.R
import java.util.regex.Matcher
import java.util.regex.Pattern

const val LOGIN_WITH_FACEBOOK = 0
const val LOGIN_WITH_GOOGLE = 1
const val LOGIN_WITH_EMAIL = 2

const val GALLERY_REQUEST = 101
const val CAMERA_REQUEST_IMAGE = 102
const val CAMERA_REQUEST_VIDEO = 103
const val ADDRESS_PICKER_REQUEST = 104

/* fragment functions*/

fun addFragmentBackStack(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    tag: String, @IdRes container: Int
) {
    fragmentManager.beginTransaction().add(container, fragment, tag).addToBackStack(tag).commit()
}

fun replaceLeftSlidingFragmentBackStack(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    tag: String, @IdRes container: Int
) {
    fragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.slide_left_in, 0, 0, R.anim.slide_left_out)
        .replace(container, fragment, tag).addToBackStack(tag).commit()
}

fun replaceSlidingFragmentBackStack(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    tag: String, @IdRes container: Int
) {
    fragmentManager.beginTransaction().setCustomAnimations(
        R.anim.slide_up_dialog, 0, 0,
        R.anim.slide_down_dialog
    ).replace(container, fragment, tag).addToBackStack(tag).commit()
}

fun replaceFragmentDrawer(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    tag: String, @IdRes container: Int
) {
    fragmentManager.beginTransaction(
    ).replace(container, fragment, tag).addToBackStack(tag).commit()
}

fun addSlidingFragmentBackStack(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    tag: String, @IdRes container: Int
) {
    if (null != fragmentManager.findFragmentById(container)) {
        fragmentManager.findFragmentById(container)?.let {
            if (!it.tag.equals(tag, true)) {
                fragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.overshoot_slide_right_in, 0, 0,
                    R.anim.overshoot_slide_right_out
                ).add(container, fragment, tag).addToBackStack(tag).commit()
            }
        }
    } else {
        fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.overshoot_slide_right_in, 0, 0,
            R.anim.overshoot_slide_right_out
        ).add(container, fragment, tag).addToBackStack(tag).commit()
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun String.isValidUserName(): Boolean =
    this.isNotBlank() && this.matches("^[a-zA-Z0-9]*$".toRegex())

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun isValidPassword(password :String ) : Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
    pattern = Pattern.compile(passwordPattern)
    matcher = pattern.matcher(password)
    return matcher.matches()
}

val screenWidth: Int get() = Resources.getSystem().displayMetrics.widthPixels

val screenHeight: Int get() = Resources.getSystem().displayMetrics.heightPixels


/* Glide Request Options */
val circularDarkRequestOptions: RequestOptions
    get() = RequestOptions().centerCrop().circleCrop().placeholder(
        R.drawable.drawable_unselected_button_background
    ).error(R.drawable.drawable_unselected_button_background)
val squareDarkRequestOptions: RequestOptions
    get() = RequestOptions().placeholder(R.drawable.drawable_rectangular_glide_loader).error(
        R.drawable.drawable_rectangular_glide_loader
    ).fitCenter()

fun openKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}


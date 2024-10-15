package com.ag_apps.auth.data

import android.util.Patterns
import com.ag_apps.auth.domain.PatternValidator

/**
 * @author Ahmed Guedmioui
 */
object EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}
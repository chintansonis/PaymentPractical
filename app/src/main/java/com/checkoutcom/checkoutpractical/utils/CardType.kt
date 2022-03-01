package com.checkoutcom.checkoutpractical.utils

import com.checkoutcom.checkoutpractical.R


/**
 * Regular expressions and images were taken from Checkout SDK on Github
 * https://github.com/checkout/checkoutkit-android/blob/master/checkoutkit/src/main/java/com/checkout/CardValidator.java
 *
 */
sealed class CardType(val pattern: String, val maxCVVLength: Int, val imgCardDrawbleId: Int) {

    object Visa : CardType("^4[0-9]{12}(?:[0-9]{3})?$", 3, R.drawable.visa)
    object MasterCard :
        CardType("^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}\$",
            3,
            R.drawable.mastercard)

    object AmericanExpress : CardType("^3[47][0-9]{5,}\$", 4, R.drawable.amex)
    object DinersClub : CardType("^3(?:0[0-5]|[68][0-9])[0-9]{4,}\$", 3, R.drawable.dinersclub)
    object Discover : CardType("^6(?:011|5[0-9]{2})[0-9]{3,}\$", 3, R.drawable.discover)
    object Jcb : CardType("^(?:2131|1800|35[0-9]{3})[0-9]{3,}\$", 3, R.drawable.jcb)
    object Default : CardType("", 3, 0)

}
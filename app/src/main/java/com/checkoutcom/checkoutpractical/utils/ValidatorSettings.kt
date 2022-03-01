package com.checkoutcom.checkoutpractical.utils

import java.util.*


const val CARD_NUMBER_LENGTH_MAX: Int = 19
const val CARD_NUMBER_LENGTH_MIN: Int = 12

const val CARD_EXP_MONTH_MAX: Int = 12
const val CARD_EXP_MONTH_MIN: Int = 1

const val CARD_EXP_YEAR_LENGTH = 4

val CURRENT_MONTH: Int = Calendar.getInstance().get(Calendar.MONTH)
val CURRENT_YEAR: Int = Calendar.getInstance().get(Calendar.YEAR)
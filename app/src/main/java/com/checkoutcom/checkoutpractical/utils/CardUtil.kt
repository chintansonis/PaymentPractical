package com.checkoutcom.checkoutpractical.utils

object CardUtil {

    private val supportedCardList: MutableList<CardType> = getAllCardTypes()

    private fun getAllCardTypes() = mutableListOf(CardType.Visa,
        CardType.MasterCard,
        CardType.AmericanExpress,
        CardType.DinersClub,
        CardType.Discover,
        CardType.Jcb)


    /*
        * Reference link: https://www.geeksforgeeks.org/luhn-algorithm/
        * This method checks the validity of a given raw card number via Luhn algorithm/modulus 10
    */
    @JvmStatic
    private fun isValidCardNumberByLuhn(stringInputCardNumber: String): Boolean {
        var sum = 0
        var isSecondDigit = false

        for (i in stringInputCardNumber.length - 1 downTo 0) {
            var d = stringInputCardNumber[i] - '0'

            if (isSecondDigit) {
                d *= 2
            }

            sum += d / 10
            sum += d % 10

            isSecondDigit = !isSecondDigit
        }

        return ((sum % 10) == 0)
    }

    /**
     * @param stringInputCardNumber
     * checking card number from list of supported card type in the app
     */
    @JvmStatic
    private fun isValidCardNumberByTypeSupport(stringInputCardNumber: String): Boolean {
        for (supportedType in supportedCardList) {
            if (stringInputCardNumber.matches(supportedType.pattern.toRegex())) {
                return true
            }
        }
        return false
    }


    @JvmStatic
    private fun isValidCardNumberLength(inputCardNumber: String): Boolean {

        return ((inputCardNumber.length >= CARD_NUMBER_LENGTH_MIN) && (inputCardNumber.length <= CARD_NUMBER_LENGTH_MAX))
    }


    @JvmStatic
    private fun isValidCardNumberValue(inputCardNumber: Long) = (inputCardNumber > 0)


    /*
    * @param inputCardNumber : the 12-19 digit number
    * checking if card is valid
    * */
    @JvmStatic
    fun isValidCardNumber(inputCardNumber: Long): Boolean {
        return isValidCardNumberValue(inputCardNumber) && isValidCardNumberLength(inputCardNumber.toString()) && isValidCardNumberByTypeSupport(
            inputCardNumber.toString()) && isValidCardNumberByLuhn(inputCardNumber.toString())
    }

    /**
     * Returns a boolean showing is the cvv is valid in relation to the card type
     *
     *
     * Used to take a card number String and provide formatting (span space characters)
     *
     * @param cvv  the card cvv
     * @param card the card object
     * @return boolean representing validity
     */
    fun isValidCvv(cvv: String, card: CardType): Boolean {

        return card.maxCVVLength == cvv.length
    }

    /**
     * This method iterates a Cards enum, and determines if the the function
     * argument matches any pattern. Based on the verification, a Cards object
     * will be returned.
     *
     * @param cardNumber the String value of a card number
     * @return Cards object for the given type found
     * @see CardType
     */
    @JvmStatic
    fun getCardType(cardNumber: String): CardType {

        // Iterate over the card card types and check what pattern matches
        if (cardNumber != "") supportedCardList.forEach { card ->
            if (cardNumber.matches(card.pattern.toRegex())) {
                return card
            }
        }
        // Return a default card if no card type is matched
        return CardType.Default
    }


    fun returnExpiryDateOfMonthYear(expirationDate: String?): Array<String>? {
        return expirationDate?.split("/")?.toTypedArray()
    }


    /*
    *    @param expirationDate : MM/YYYY
    * */
    @JvmStatic
    fun isValidExpirationDate(expirationDate: String): Boolean {
        val expDateParts: Array<String> = expirationDate.split("/").toTypedArray()

        if (expDateParts.size == 1 || expDateParts[0].isEmpty() || expDateParts[0].length != 2 || expDateParts[1].isEmpty() || expDateParts[1].length != 4) {
            return false
        }

        val inputExpMonth = expDateParts[0].toInt()
        val inputExpYear = expDateParts[1].toInt()

        val isValidMonthRange =
            ((inputExpMonth >= CARD_EXP_MONTH_MIN) && (inputExpMonth <= CARD_EXP_MONTH_MAX))
        val isValidYearValue = (inputExpYear > 0)
        val isValidYearLength = (inputExpYear.toString().length == CARD_EXP_YEAR_LENGTH)

        val isFutureYear = (inputExpYear > CURRENT_YEAR)
        val isSameYear_FutureOrCurrentMonth =
            ((inputExpYear == CURRENT_YEAR) && (inputExpMonth >= CURRENT_MONTH))

        return ((isValidMonthRange && isValidYearLength && isValidYearValue) && (isFutureYear || isSameYear_FutureOrCurrentMonth))
    }


}
package com.checkoutcom.checkoutpractical.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CardUtilTest {
    // VISA
    @Test
    fun `visa card IsValid`() {
        assertEquals(true, CardUtil.isValidCardNumber(4242424242424242))
    }

    @Test
    fun `visa card IsNotValid`() {
        assertEquals(false, CardUtil.isValidCardNumber(424242424242654))
    }

    @Test
    fun `visa card IsValidCvv`() {
        assertEquals(true, CardUtil.isValidCvv("123", CardUtil.getCardType("4242424242424242")))
    }

    @Test
    fun `visa card IsNotValidCvv`() {
        assertEquals(false, CardUtil.isValidCvv("1234", CardUtil.getCardType("4242424242424242")))
    }

    @Test
    fun `visa card IsFound`() {
        assertEquals("Visa", CardUtil.getCardType("4242424242424242").cardTypename)
    }

    @Test
    fun `visa card IsNotFound`() {
        assertEquals("Default", CardUtil.getCardType("42424242424242412").cardTypename)
    }

    // mastercard
    @Test
    fun `master card IsValid`() {
        assertEquals(true, CardUtil.isValidCardNumber(5436031030606378))
    }

    @Test
    fun `master card IsNotValid`() {
        assertEquals(false, CardUtil.isValidCardNumber(5436031030606589))
    }

    @Test
    fun `master card IsValidCvv`() {
        assertEquals(true, CardUtil.isValidCvv("123", CardUtil.getCardType("4242424242424242")))
    }

    @Test
    fun `master card IsNotValidCvv`() {
        assertEquals(false, CardUtil.isValidCvv("1234", CardUtil.getCardType("4242424242424242")))
    }

    @Test
    fun `master card IsFound`() {
        assertEquals("MasterCard", CardUtil.getCardType("5436031030606378").cardTypename)
    }

    @Test
    fun `master card IsNotFound`() {
        assertEquals("Default", CardUtil.getCardType("1234567891234567").cardTypename)
    }

    // AmericanExpress
    @Test
    fun `americanExpress card IsValid`() {
        assertEquals(true, CardUtil.isValidCardNumber(345678901234564))
    }

    @Test
    fun `americanExpress card IsNotValid`() {
        assertEquals(false, CardUtil.isValidCardNumber(345678901234555))
    }

    @Test
    fun `americanExpress card IsValidCvv`() {
        assertEquals(true, CardUtil.isValidCvv("1234", CardUtil.getCardType("345678901234564")))
    }

    @Test
    fun `americanExpress card IsNotValidCvv`() {
        assertEquals(false, CardUtil.isValidCvv("124", CardUtil.getCardType("345678901234564")))
    }

    @Test
    fun `americanExpress card IsFound`() {
        assertEquals("AmericanExpress", CardUtil.getCardType("345678901234564").cardTypename)
    }

    @Test
    fun `americanExpress card IsNotFound`() {
        assertEquals("Default", CardUtil.getCardType("1234567891234567").cardTypename)
    }

    // DinersClub
    @Test
    fun `dinersClub card IsValid`() {
        assertEquals(true, CardUtil.isValidCardNumber(30123456789019))
    }

    @Test
    fun `dinersClub card IsNotValid`() {
        assertEquals(false, CardUtil.isValidCardNumber(30123456712345))
    }

    @Test
    fun `dinersClub card IsValidCvv`() {
        assertEquals(true, CardUtil.isValidCvv("124", CardUtil.getCardType("30123456789019")))
    }

    @Test
    fun `dinersClub card IsNotValidCvv`() {
        assertEquals(false, CardUtil.isValidCvv("1254", CardUtil.getCardType("30123456789019")))
    }

    @Test
    fun `dinersClub card IsFound`() {
        assertEquals("DinersClub", CardUtil.getCardType("30123456789019").cardTypename)
    }

    @Test
    fun `dinersClub card IsNotFound`() {
        assertEquals("Default", CardUtil.getCardType("1234567891234567").cardTypename)
    }

    // Discover
    @Test
    fun `discover card IsValid`() {
        assertEquals(true, CardUtil.isValidCardNumber(30123456789019))
    }

    @Test
    fun `discover card IsNotValid`() {
        assertEquals(false, CardUtil.isValidCardNumber(6011111111112345))
    }

    @Test
    fun `discover card IsValidCvv`() {
        assertEquals(true, CardUtil.isValidCvv("124", CardUtil.getCardType("6011111111111117")))
    }

    @Test
    fun `discover card IsNotValidCvv`() {
        assertEquals(false, CardUtil.isValidCvv("1254", CardUtil.getCardType("6011111111111117")))
    }

    @Test
    fun `discover card IsFound`() {
        assertEquals("Discover", CardUtil.getCardType("6011111111111117").cardTypename)
    }

    @Test
    fun `discover card IsNotFound`() {
        assertEquals("Default", CardUtil.getCardType("12345678912345").cardTypename)
    }

    // Jcb
    @Test
    fun `jcb card IsValid`() {
        assertEquals(true, CardUtil.isValidCardNumber(3530111333300000))
    }

    @Test
    fun `jcb card IsNotValid`() {
        assertEquals(false, CardUtil.isValidCardNumber(3530111333312345))
    }

    @Test
    fun `jcb card IsValidCvv`() {
        assertEquals(true, CardUtil.isValidCvv("124", CardUtil.getCardType("3530111333300000")))
    }

    @Test
    fun `jcb card IsNotValidCvv`() {
        assertEquals(false, CardUtil.isValidCvv("1254", CardUtil.getCardType("3530111333300000")))
    }

    @Test
    fun `jcb card IsFound`() {
        assertEquals("Jcb", CardUtil.getCardType("3530111333300000").cardTypename)
    }

    @Test
    fun `jcb card IsNotFound`() {
        assertEquals("Default", CardUtil.getCardType("1234567891234567").cardTypename)
    }

}
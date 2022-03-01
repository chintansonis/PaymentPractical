# PaymentPracticalTask


https://user-images.githubusercontent.com/13369510/156165731-e5b07da8-7038-438e-8fb7-4985566a9c3e.mp4

## App Overview
This practical task is a small demo application based on modern Android tech-stacks especially focus on MVVM design pattern using clean architecture. It has features to detect card types and validations for it.
</br>

## App Functionalities
User can add any debit cards and placing static 3d secure payment. </br>
Features:
1. detecting cards Visa,MasterCard, AmericanExpress, DinersClub,Discover, Jcb.
2. Managed optimal scenario for validation of card like card number and cvv validation etc.
References for Card validation:
1. Credit card numbers validation references taken from https://github.com/checkout/frames-android/tree/master/android-sdk/src/main/java/com/checkout/android_sdk </br>
2 Credit card numbers also needed to pass Luhn validation - see https://en.wikipedia.org/wiki/Luhn_algorithm </br>
3 The CVV had to meet these criteria: Reference from checkout SDK to understand validation

## App Clean architecture, design pattern and latest techstack reference
1. MVVM Design pattern</br>
2. Clean architecture: https://www.raywenderlich.com/3595916-clean-architecture-tutorial-for-android-getting-started</br>
3. Guide to app architecture: https://developer.android.com/jetpack/guide</br>
4. Data binding: https://developer.android.com/topic/libraries/data-binding
5. Hilt for depedency injection
6. Retrofit for webservice integration
7. Jetpack navigation : https://developer.android.com/guide/navigation
8. written basic local unit test for card validation

## Configuration Details:
1. Studio version: Android Studio Dolphin | 2021.3.1 Canary 2
2. Min SDK: 21, compileSdk :  32
3. Kotlin version: 1.6.10

 

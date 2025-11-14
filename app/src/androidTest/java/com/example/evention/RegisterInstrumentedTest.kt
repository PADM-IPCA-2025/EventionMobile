package com.example.evention

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.evention.ui.screens.auth.register.RegisterScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private fun launchScreen() {
        rule.setContent {
            RegisterScreen(navController = FakeNavController())
        }
    }

    // ---------------------------
    // TC1.1 – Email inválido
    // ---------------------------
    @Test
    fun testInvalidEmailShowsAlert() {
        launchScreen()

        rule.onNodeWithText("Full Name").performTextInput("Carlos")
        rule.onNodeWithText("abc@email.com")
        rule.onNodeWithText("abcemail.com").performTextInput("abcemail.com")

        rule.onNodeWithText("Sign up").performClick()

        rule.onNodeWithText("Email inválido").assertIsDisplayed()
    }

    // ---------------------------
    // TC1.2 – Password < 8 chars
    // ---------------------------
    @Test
    fun testShortPasswordShowsAlert() {
        launchScreen()

        rule.onNodeWithText("Full Name").performTextInput("Maria")
        rule.onNodeWithText("abc@email.com").performTextInput("maria@gmail.com")
        rule.onNodeWithText("Your Password").performTextInput("123")
        rule.onNodeWithText("Confirm Password").performTextInput("123")

        rule.onNodeWithText("Sign up").performClick()

        rule.onNodeWithText("A password deve ter pelo menos 8 caracteres")
            .assertIsDisplayed()
    }

    // ---------------------------
    // TC1.3 – Clicar Sign up sem preencher nada
    // ---------------------------
    @Test
    fun testEmptyFieldsShowsRequiredWarnings() {
        launchScreen()

        rule.onNodeWithText("Sign up").performClick()

        rule.onNodeWithText("Campo obrigatório").assertIsDisplayed()
    }

    // ---------------------------
    // TC1.4 – Registo correcto
    // ---------------------------
    @Test
    fun testSuccessfulRegistration() {
        launchScreen()

        rule.onNodeWithText("Full Name").performTextInput("João Silva")
        rule.onNodeWithText("abc@email.com").performTextInput("joao@gmail.com")
        rule.onNodeWithText("Your Password").performTextInput("Password123")
        rule.onNodeWithText("Confirm Password").performTextInput("Password123")

        rule.onNodeWithText("Sign up").performClick()

        rule.onNodeWithText("User registed successfully!")
            .assertIsDisplayed()
    }
}

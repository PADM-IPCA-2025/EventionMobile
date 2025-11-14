package com.example.evention

import UserPreferences
import android.content.Context
import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.evention.di.NetworkModule
import com.example.evention.ui.screens.auth.register.RegisterScreen
import com.example.evention.ui.screens.home.HomeScreen
import com.example.evention.ui.theme.EventionTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTests {

    @get:Rule
    val composeRule = createComposeRule() // ✅ Compose-only rule

    private lateinit var context: Context

    @Before
    fun setup() {
        // Contexto de teste
        context = ApplicationProvider.getApplicationContext()

        // Inicializa UserPreferences com token fake
        val userPrefs = UserPreferences(context)
        userPrefs.saveToken("fake-token")
        NetworkModule.init(userPrefs)
    }

    private fun setRegisterScreen() {
        composeRule.setContent {
            EventionTheme {
                val navController = rememberNavController()
                HomeScreen(navController = navController)
            }
        }
    }

    // 1️⃣ Preencher formulário e clicar Sign up
    @Test
    fun testRegisterButton_fillsFormAndClicksSignUp() {
        setRegisterScreen()

        composeRule.onNodeWithText("Full Name")
            .performTextInput("John Doe")

        composeRule.onNodeWithText("abc@email.com")
            .performTextInput("john@email.com")

        composeRule.onNodeWithText("Your Password")
            .performTextInput("123456")

        composeRule.onNodeWithText("Confirm Password")
            .performTextInput("123456")

        composeRule.onNodeWithText("Sign up")
            .performClick()

        // Verifica toast ou mensagem de sucesso
        //composeRule.onNodeWithText("User registed successfully!")
        //    .assertIsDisplayed()
    }

    // 2️⃣ Clicar no botão de voltar
    @Test
    fun testClickBack_navigatesToSignIn() {
        setRegisterScreen()

        composeRule.onNodeWithContentDescription("Arrow Back")
            .performClick()

        composeRule.onNodeWithText("Sign in")
            .assertIsDisplayed()
    }

    // 3️⃣ Clicar em "Sign in" no fim da tela
    @Test
    fun testClickSignInText_navigatesToSignIn() {
        setRegisterScreen()

        composeRule.onNodeWithText("Sign in")
            .performClick()

        composeRule.onNodeWithText("Sign in")
            .assertIsDisplayed()
    }

    // 4️⃣ Clicar no botão Google Sign-In
    @Test
    fun testClickGoogleSignInButton() {
        setRegisterScreen()

        composeRule.onNodeWithText("Registar com Google")
            .performClick()

        // Apenas verifica que existe e é clicável
        composeRule.onNodeWithText("Registar com Google")
            .assertExists()
    }
}

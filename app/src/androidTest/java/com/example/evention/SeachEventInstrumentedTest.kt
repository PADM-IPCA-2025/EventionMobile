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
import androidx.test.platform.app.InstrumentationRegistry

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule() // ✅ Compose-only rule

    private lateinit var context: Context

    // Para conceder permissão automaticamente (TC3.2)
    @get:Rule
    val locationPermissionRule = androidx.test.rule.GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

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
        composeTestRule.setContent {
            EventionTheme {
                val navController = rememberNavController()
                HomeScreen(navController = navController)
            }
        }
    }

    /**
     * TC3.1 - Teste da pesquisa por nome de evento
     * Pré-requisito: Fazer Login (assumindo que MainActivity já abre Home após login simulado)
     */
    @Test
    fun testSearchEventByName() {
        val eventName = "Barcelos Party"

        // PASSO 1: Verificar que a página de eventos aparece
        composeTestRule.onNodeWithText("Upcoming Events")
            .assertIsDisplayed()

        // PASSO 2: Introduzir o nome do evento na pesquisa
        composeTestRule.onNodeWithTag("SearchTextField")
            .performTextInput(eventName)

        composeTestRule.onNodeWithTag("SearchTextField")
            .assert(hasText(eventName))

        // PASSO 3: Carregar no botão de pesquisa
        composeTestRule.onNodeWithContentDescription("Search Button")
            .performClick()

        // RESULTADO ESPERADO: Deve aparecer uma lista contendo o evento pesquisado
        composeTestRule.onNodeWithText(eventName)
            .assertIsDisplayed()
    }


    /**
     * TC3.2 -
     * Pré-requisito:
     */
    @Test
    fun testSearchByUserLocation_acceptPermission() {
        // PASSO 1: Verificar que a página de eventos aparece
        composeTestRule.onNodeWithText("Upcoming Events")
            .assertIsDisplayed()

        // PASSO 2: Clicar no botão de "Minha Localização"
        composeTestRule.onNodeWithContentDescription("My Location Button")
            .performClick()

        // PASSO 3: Usuário aceita permissão (GrantPermissionRule já faz isso)
        // Com isso, o app deve mostrar a localização e eventos próximos

        // Verificar que o mapa aparece (assumindo que seu mapa tem testTag)
        composeTestRule.onNodeWithTag("MapView")
            .assertIsDisplayed()

        // Verificar que pelo menos 1 evento próximo é mostrado
        composeTestRule.onNodeWithTag("NearbyEventCard")
            .assertIsDisplayed()
    }


    /**
     * TC3.3 -
     * Pré-requisito:
     */
    @Test
    fun testSearchByUserLocation_denyPermission() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // PASSO 1: Verificar que a página de eventos aparece
        composeTestRule.onNodeWithText("Upcoming Events")
            .assertIsDisplayed()

        // PASSO 2: Clicar no botão de "Minha Localização"
        composeTestRule.onNodeWithContentDescription("My Location Button")
            .performClick()

        // PASSO 3: Negar permissão usando UiAutomator
        val denyButton = device.findObject(
            androidx.test.uiautomator.By.textMatches("(?i)deny|recusar")
        )
        if (denyButton.exists()) {
            denyButton.click()
        }

        // RESULTADO ESPERADO: A mensagem de erro aparece
        composeTestRule.onNodeWithText("Sem acesso à localização")
            .assertIsDisplayed()
    }
}

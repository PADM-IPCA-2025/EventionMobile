package com.example.evention

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun RegisterScreen(navController: NavController? = null)
{

    override fun navigate(route: String, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?) {
        // Não faz nada — só para testes
    }

    override fun popBackStack(): Boolean {
        return true
    }
}

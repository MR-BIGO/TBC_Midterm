package com.example.tbc_midterm_project.presentation.event.home

sealed class HomeEvents {
    data object OffersPressed : HomeEvents()
    data object CollectionPressed : HomeEvents()
    data object CalculatorPressed : HomeEvents()
    data object SignInPressed : HomeEvents()
    data object LogOutPressed : HomeEvents()
}
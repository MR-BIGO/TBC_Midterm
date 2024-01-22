package com.example.tbc_midterm_project.presentation.event.offer

sealed class OfferEvents {
    data class OfferPressed(val id: Int, val auth: Boolean): OfferEvents()
    data object ResetError: OfferEvents()
    data object BackPressed: OfferEvents()
    data object LogInPressed: OfferEvents()
}
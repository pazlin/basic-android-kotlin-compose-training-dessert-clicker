package com.example.dessertclicker

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    private var lista:List<Dessert> = Datasource.dessertList


    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState->
            currentState.copy(currentDessertPrice = lista[0].price, currentDessertImageId = lista[0].imageId)
        }
    }


    // Update the revenue
    fun updateRevenue(){
        var newRevenue = _uiState.value.revenue + _uiState.value.currentDessertPrice
        var sold = _uiState.value.dessertsSold.inc()
        _uiState.update { current->
            current.copy(revenue = newRevenue, dessertsSold = sold )
        }

    }

    fun determineDessertToShow() {
        var dessertToShow = lista.first()
        for (dessert in lista) {
            if (_uiState.value.dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                break
            }
        }
        _uiState.update { current->
            current.copy(currentDessertImageId = dessertToShow.imageId, currentDessertPrice = dessertToShow.price)
        }

    }





}
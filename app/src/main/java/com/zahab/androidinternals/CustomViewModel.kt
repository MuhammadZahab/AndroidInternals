package com.zahab.androidinternals

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class CustomViewModel {

    protected val viewModelScope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    open fun onCleared() {
        viewModelScope.cancel()
    }
}

class CustomViewModelStore {
    val viewModels = HashMap<String, CustomViewModel>()

    fun <T : CustomViewModel> put(viewModel: T) {
        viewModels.put(viewModel::class.java.simpleName, viewModel)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : CustomViewModel> get(clazz: Class<T>): T? {
        return viewModels[clazz.simpleName] as? T
    }

    fun clear() {
        viewModels.values.onEach {
            it.onCleared()
        }

        viewModels.clear()
    }

}

interface CustomViewModelFactory {
    fun <T : CustomViewModel> create(modelClass: Class<T>): T

}

object DefaultCustomViewModelFactory : CustomViewModelFactory {
    override fun <T : CustomViewModel> create(modelClass: Class<T>): T {
        return modelClass.getDeclaredConstructor().newInstance()
    }
}

class CustomViewModelProvider(
    private val store: CustomViewModelStore,
    private val factory: CustomViewModelFactory = DefaultCustomViewModelFactory
) {

    fun <T : CustomViewModel> get(modelClass: Class<T>): T {
        val existing = store.get(modelClass)

        if (existing != null && modelClass.isInstance(existing)) {
            return existing
        }

        val viewModel = factory.create(modelClass)
        store.put(viewModel)

        return viewModel
    }
}


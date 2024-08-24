package com.example.kaamapp.models.repository

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kaamapp.models.data.Priority
import com.example.kaamapp.utils.Constant.PREFERENCE_KEY
import com.example.kaamapp.utils.Constant.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore by preferencesDataStore(
    name = PREFERENCE_NAME
)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context:Context
){
    private object PreferenceKeys{
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
    }
    private val dataStore = context.dataStore
    suspend fun persistStoreState(priority: Priority){
        dataStore.edit { preference->
            preference[PreferenceKeys.sortKey] = priority.name
        }
    }
    val readSortState : Flow<Unit> = dataStore.data.catch {
        exception->
        if(exception is IOException){
            emit(emptyPreferences())
        }else{
            throw exception
        }
    }.map { preferences->
        val sortState = preferences[PreferenceKeys.sortKey]?:Priority.None
    }

}
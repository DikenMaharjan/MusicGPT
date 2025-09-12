package com.example.musicplayer.feature.generate_music.data.infrastructure.data_source

import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryMusicGenerationRecords @Inject constructor() {

    private val _musicGenerationRecords = MutableStateFlow(mapOf<String, MusicGenerationRecord>())
    val musicGenerationRecords = _musicGenerationRecords.asStateFlow()


    fun upsert(musicGenerationRecord: MusicGenerationRecord) {
        _musicGenerationRecords.update { initial ->
            initial + (musicGenerationRecord.id to musicGenerationRecord)
        }
    }

    fun delete(musicGenerationRecord: MusicGenerationRecord) {
        _musicGenerationRecords.update { initial ->
            initial - musicGenerationRecord.id
        }
    }
}
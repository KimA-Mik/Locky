package com.github.kima_mik.locky.presentation.android.dataStore.appData

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.github.kima_mik.locky.data.applicationData.AppDataDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
object AppDataSerializer : Serializer<AppDataDto> {
    override val defaultValue: AppDataDto
        get() = AppDataDto()

    override suspend fun readFrom(input: InputStream): AppDataDto {
        return try {
            withContext(Dispatchers.IO) {
                ProtoBuf.decodeFromByteArray<AppDataDto>(input.readBytes())
            }
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AppDataDto, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(ProtoBuf.encodeToByteArray(t))
        }
    }
}
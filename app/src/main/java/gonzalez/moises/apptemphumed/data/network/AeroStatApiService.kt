package gonzalez.moises.apptemphumed.data.network

import gonzalez.moises.apptemphumed.data.models.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AeroStatApiService {
    @GET("sensors/s1/latest")
    suspend fun getLatestData(
        @Header("Authorization") token: String
    ): SensorResponse

    @GET("sensors/s1/history")
    suspend fun getHistory(
        @Query("limite") limite: Int = 20,
        @Header("Authorization") token: String
    ): List<HistoryPoint>
}
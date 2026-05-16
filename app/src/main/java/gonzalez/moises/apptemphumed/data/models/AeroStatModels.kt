package gonzalez.moises.apptemphumed.data.models

data class SensorResponse(
    val id: Int,
    val actual: ActualData,
    val analisis: AnalisisData
)

data class ActualData(
    val temperatura: Double,
    val humedad: Double,
    val foto_url: String,
    val fecha_hora: String
)

data class AnalisisData(
    val variacion_temperatura: Double,
    val variacion_humedad: Double,
    val mensaje_temperatura: String,
    val mensaje_humedad: String
)

data class HistoryPoint(
    val id: Int,
    val temperatura: Double,
    val humedad: Double,
    val fecha_hora: String,
    val fecha_completa: String
)
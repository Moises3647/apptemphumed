# Requisitos del Proyecto - AeroStat

Este documento detalla los requisitos técnicos y las dependencias necesarias para compilar y ejecutar la aplicación AeroStat.

## 📱 Requisitos del Dispositivo
- **Sistema Operativo**: Android 10.0 (API nivel 29) o superior.
- **Conexión a Internet**: Requerida para la sincronización de datos con el servidor.

## 💻 Requisitos de Desarrollo
- **Android Studio**: Ladybug | 2024.2.1 o superior recomendado.
- **Java Development Kit (JDK)**: Versión 11.
- **Gradle**: Versión compatible con el plugin de Android 9.0.1.
- **Kotlin**: Versión 2.0.21.

## 📚 Dependencias Principales
La aplicación utiliza las siguientes librerías:

### UI & UX
- **Jetpack Compose (BOM 2024.09.00)**: Framework moderno para la interfaz de usuario.
- **Material Design 3**: Componentes de diseño actuales.
- **Navigation Compose (2.8.5)**: Gestión de rutas y pantallas.
- **Material Icons Extended**: Set ampliado de iconos.

### Networking & Datos
- **Retrofit (2.9.0)**: Cliente HTTP para el consumo de la API.
- **Gson (2.9.0)**: Conversión de JSON a objetos Kotlin.

### Arquitectura
- **Lifecycle ViewModel Compose (2.6.1)**: Manejo del estado de la UI y ciclo de vida.
- **StateFlow & Coroutines**: Para el manejo de flujos de datos asíncronos.

## 🔐 Permisos
La aplicación requiere los siguientes permisos declarados en el `AndroidManifest.xml`:
- `android.permission.INTERNET`: Para acceder a los servicios web.

## 🛠️ Configuración de API
El servicio de red espera una API REST con los siguientes endpoints:
- `GET /sensors/s1/latest`: Obtiene los datos más recientes.
- `GET /sensors/s1/history`: Obtiene el historial de mediciones.
- **Nota**: Se requiere un token de autorización en las cabeceras de las peticiones.

# 📝 Editor Colaborativo en Tiempo Real – Android (Jetpack Compose)

Este proyecto implementa un editor colaborativo donde múltiples usuarios pueden escribir simultáneamente, sincronizar el texto en tiempo real y ver quién más está conectado. Está construido usando **Jetpack Compose** y **Firebase**.

---

## 🚀 Tecnologías

- Jetpack Compose
- Firebase Firestore
- Firebase Authentication (Anónima)
- ViewModel + StateFlow

---

## 🎯 Funcionalidades

- Editor de texto en tiempo real
- Contador de usuarios conectados
- Notificación visual cuando alguien entra o sale
- Limpieza automática del usuario al cerrar o salir de la app

---

## ⚙️ Configuración

1. Crea un proyecto en [Firebase Console](https://console.firebase.google.com/)
2. Activa **Firestore Database** y **Authentication (Anónima)**
3. Descarga el archivo `google-services.json`
4. Coloca el archivo dentro del directorio `/app`

---

## ▶️ Cómo correr el proyecto

1. Abre el proyecto en Android Studio
2. Verifica que `google-services.json` esté en la carpeta `/app`
3. Sincroniza el proyecto (Gradle sync)
4. Ejecuta en emulador o dispositivo

---

## 📄 Archivos principales

- `OnlineDocActivity.kt` – Vista principal y edición de texto
- `OnlineDocViewModel.kt` – Lógica de sincronización y conexión de usuarios
- `FirebaseAppInitializer.kt` – Inicialización de Firebase al arrancar la app

---

## 🧪 Prueba colaborativa

Ejecuta la app en dos emuladores o teléfonos al mismo tiempo. Escribe en uno y observa cómo el texto y el contador se actualizan al instante.

---

## 📬 Autor

Desarrollado por [@kevinhomorales](https://github.com/kevinhomorales)

---

## 📝 Licencia

MIT License

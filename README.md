#  HabitForge

<p align="center">
  <img src="AppMovil/app/src/main/res/drawable/logohabitforge.png" alt="HabitForge Logo" width="200">
</p>

> Construye hábitos. Sube de nivel.

---

## 👥 Miembros del equipo

| Nombre | GitHub |
|--------|--------|
| Joshua Vilchez| [@J-nextwebdev](https://github.com/J-nextwebdev) |
| Kevin Medina | [@Kevinmedi](https://github.com/kevinmedi) |
| Enrique Solis | [@NextVibeCoder](https://github.com/NextVibeCoder)  |

---

## 📖 Descripción del proyecto

**HabitForge** es una aplicación móvil para Android que ayuda a los usuarios a construir y mantener hábitos personales mediante un sistema de gamificación. Los usuarios pueden crear hábitos diarios o semanales, registrar su cumplimiento cada día y visualizar su progreso a través de rachas y calendarios de cumplimiento.

La app permite además compartir hábitos con amigos y competir en rachas, incentivando la constancia a través de la comunidad.

Desarrollado como proyecto final para la clase de **Programación Orientada a Objetos 2** con el profesor Norman Cash.

---

## 🛠️ Tecnologías utilizadas

###Frontend
| Tecnología | Uso |
|------------|-----|
| [Kotlin](https://kotlinlang.org/) | Lenguaje principal de desarrollo |
| [Jetpack Compose](https://developer.android.com/jetpack/compose) | UI declarativa |
| [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) | Navegación entre pantallas |
| [Retrofit](https://square.github.io/retrofit/) | Comunicación con la API REST |
| [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) | Gestión de estado y ciclo de vida de la UI |

###Backend
| Tecnología | Uso |
|------------|-----|
| [Spring Boot](https://spring.io/projects/spring-boot) | Framework de desarrollo |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa) | ORM |
| [PostgreSQL](https://www.postgresql.org/) | Base de datos |



---

## ▶️ Instrucciones para ejecutar el proyecto

Para ejecutar el proyecto completo (Frontend + Backend), sigue los requisitos y pasos que se detallan a continuación.

### 📋 Requisitos Previos Generales

Antes de comenzar, asegúrate de tener instalado:
* **Java JDK 17** (para compilar y ejecutar el backend de Spring Boot).
* **Android Studio** (versión recomendada Ladybug o superior, con el SDK de Android configurado).
* **PostgreSQL** (base de datos relacional para el almacenamiento persistente).


---

### 🖥️ 1. Backend (Spring Boot) - [ApiRest/HabitForgeApi]

El backend expone una API RESTful en el puerto `8181` y requiere una base de datos PostgreSQL activa.

#### Configuración de Variables de Entorno
El backend utiliza variables de entorno. Configura las siguientes variables en tu sistema o en tu IDE antes de arrancar la aplicación:
* `DB_URL`: URL de conexión a tu base de datos PostgreSQL (ej. `jdbc:postgresql://localhost:5432/habitforge_db`).
* `DB_USERNAME`: Usuario de la base de datos PostgreSQL.
* `DB_PASSWORD`: Contraseña del usuario de la base de datos.
* `SECRET`: Clave secreta utilizada para la firma de tokens JWT.

#### Pasos para ejecutar:
1. Asegúrate de haber creado la base de datos en PostgreSQL.
2. Navega a la carpeta del backend:
   ```bash
   cd ApiRest/HabitForgeApi
   ```
3. Ejecuta el servidor usando el Maven Wrapper:
   * **Windows (PowerShell/CMD):**
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```
   * **Linux/macOS:**
     ```bash
     chmod +x mvnw
     ./mvnw spring-boot:run
     ```
4. El servidor backend estará activo en `http://localhost:8181`.

---

### 📱 2. Frontend (Android) - [AppMovil] 

La aplicación móvil está construida con Kotlin y Jetpack Compose, y se comunica con la API REST del backend.

#### Configuración de Conexión
* Si estás ejecutando la aplicación en el **Emulador de Android Studio**, debes apuntar las llamadas de Retrofit/API a la dirección `http://10.0.2.2:8181` para que pueda comunicarse con el backend que corre localmente en tu máquina.
* Si estás usando un **dispositivo físico**, asegúrate de que tanto el dispositivo como el servidor backend estén conectados a la misma red  y configura la URL base en retrofit con la IP4de tu máquina (ej. `http://192.168.1.XX:8181`).

#### Pasos para ejecutar:
1. Abre **Android Studio**.
2. Selecciona **Open** y abre la carpeta [AppMovil]
3. Espera a que termine la sincronización del proyecto con Gradle.
4. Conecta un dispositivo físico con la Depuración USB activada o inicia un Emulador de Android.
5. Haz clic en el botón **Run** (ícono de play verde) o presiona `Shift + F10` en Windows/Linux para compilar e instalar la app en el dispositivo.


---


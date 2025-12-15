# Cat Breeds Explorer

## Funcionalidades
- Búsqueda de razas por nombre, origen o temperamento.
- Listado en “Explorar” con imagen principal (si existe).
- Guardado y gestión de favoritos en base de datos local (Room).
- Al entrar a “Mis Favoritos”, la app intenta actualizar automáticamente la información desde la API.
- Manejo de estados en UI: loading, error, sin conexión / datos sin actualizar y lista vacía.

## Stack y requisitos cumplidos
- Android nativo con Kotlin (proyecto 100% Kotlin).
- Jetpack Compose (UI declarativa).
- MVVM + Clean Architecture:
  - `domain`: modelos, interfaces de repositorio y casos de uso.
  - `data`: Room + API + mappers.
  - `presentation`: ViewModels, UiState y pantallas Compose.
- Room (SQLite) y funcionamiento offline:
  - Los favoritos quedan persistidos y se muestran aunque no haya internet.
  - Room actúa como “source of truth” para la pantalla de favoritos.
- Koin para inyección de dependencias.
- ViewModel + estado en Compose (StateFlow + collectAsState).
- Ktor para comunicación REST.
- Kotlinx Serialization para JSON.
- Coil para carga de imágenes (AsyncImage).

## API utilizada
Se utiliza The Cat API:
- Razas:
  - `GET /v1/breeds`
  - `GET /v1/breeds/search?q={query}`
- Imágenes:
  - `GET /v1/images/{image_id}` (a partir de `reference_image_id`)

> Documentación oficial: https://thecatapi.com/


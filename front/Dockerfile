# Usa la imagen de Bun como base
FROM oven/bun:latest AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto al contenedor
COPY . .

# Establece la variable de entorno para Vite antes de construir
ENV VITE_URL_BASE=http://4.236.176.137:8081

# Instala las dependencias y construye el proyecto
RUN bun install --frozen-lockfile
RUN bun run build

# Segunda etapa: Servidor con Bun
FROM oven/bun:latest

WORKDIR /app

# Copia los archivos generados en `dist/` al nuevo contenedor
COPY --from=builder /app/dist /app/dist

# Instala un servidor estático en Bun (opcional si no está incluido en el proyecto)
RUN bun add sirv-cli

# Expone el puerto 3000 (puedes cambiarlo)
EXPOSE 3000

# Usa Bun para servir los archivos estáticos
CMD ["bun", "x", "sirv", "dist", "--host", "--port", "3000"]

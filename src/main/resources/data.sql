-- Carga inicial de palabras para el juego Hangman
-- Palabras de al menos 10 caracteres en español

INSERT INTO words (palabra, utilizada) VALUES 
('PROGRAMADOR', false),
('COMPUTADORA', false),
('TECNOLOGIA', false),
('INFORMATICA', false),
('DESARROLLO', false),
('APLICACION', false),
('PLATAFORMA', false),
('ARQUITECTURA', false),
('IMPLEMENTACION', false),
('FUNCIONALIDAD', false),
('REQUERIMIENTO', false),
('DOCUMENTACION', false),
('PROCESAMIENTO', false),
('CONFIGURACION', false),
('ADMINISTRACION', false),
('ESPECIALIZACION', false),
('OPTIMIZACION', false),
('CARACTERISTICA', false),
('DISTRIBUCION', false),
('ORGANIZACION', false);

INSERT INTO players (nombre, fecha) VALUES 
('Juan Pérez', '2025-01-15'),
('María Gómez', '2025-01-16');


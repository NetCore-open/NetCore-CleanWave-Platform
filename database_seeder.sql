-- =========================================================================
-- CLEANWAVE DATABASE SEEDER (CARGA MODERADA Y REALISTA)
-- =========================================================================
SET FOREIGN_KEY_CHECKS = 0;
-- Asegurar que las columnas Enum sean VARCHAR
ALTER TABLE deliveries
MODIFY COLUMN type VARCHAR(50);
ALTER TABLE deliveries
MODIFY COLUMN status VARCHAR(50);
ALTER TABLE orders
MODIFY COLUMN status VARCHAR(50);
ALTER TABLE plans
MODIFY COLUMN type VARCHAR(50);
ALTER TABLE plans
MODIFY COLUMN billing_period VARCHAR(50);
ALTER TABLE notifications
MODIFY COLUMN type VARCHAR(50);
ALTER TABLE notifications
MODIFY COLUMN priority VARCHAR(50);
TRUNCATE TABLE notifications;
TRUNCATE TABLE deliveries;
TRUNCATE TABLE order_items;
TRUNCATE TABLE orders;
TRUNCATE TABLE subscriptions;
TRUNCATE TABLE plans;
TRUNCATE TABLE laundries;
TRUNCATE TABLE profiles;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS = 1;
-- =========================================================================
-- 1. USUARIOS (Clave: 123456) -> 3 Usuarios (1 Admin, 2 Clientes)
-- =========================================================================
INSERT INTO users (id, username, password, created_at, updated_at)
VALUES (
        1,
        'carlos@cleanwave.com',
        '$2a$10$8SWC7QeqOGeE0SsjAYmq6efH4ogCmpBZ8hKgQlJnpKOBWG7C5ed/6',
        NOW(),
        NOW()
    ),
    (
        2,
        'andrea@ejemplo.com',
        '$2a$10$8SWC7QeqOGeE0SsjAYmq6efH4ogCmpBZ8hKgQlJnpKOBWG7C5ed/6',
        NOW(),
        NOW()
    ),
    (
        3,
        'luis@ejemplo.com',
        '$2a$10$8SWC7QeqOGeE0SsjAYmq6efH4ogCmpBZ8hKgQlJnpKOBWG7C5ed/6',
        NOW(),
        NOW()
    );
INSERT INTO user_roles (user_id, role)
VALUES (1, 'ROLE_ADMIN'),
    (2, 'ROLE_CLIENT'),
    (3, 'ROLE_CLIENT');
-- =========================================================================
-- 2. PERFILES
-- =========================================================================
INSERT INTO profiles (
        id,
        first_name,
        last_name,
        email_address,
        street_address_street,
        street_address_number,
        street_address_city,
        street_address_postal_code,
        street_address_country,
        created_at,
        updated_at
    )
VALUES (
        1,
        'Carlos',
        'Admin',
        'carlos@cleanwave.com',
        'Av. Primavera',
        '456',
        'Lima',
        '15038',
        'Peru',
        NOW(),
        NOW()
    ),
    (
        2,
        'Andrea',
        'Gómez',
        'andrea@ejemplo.com',
        'Av. Larco',
        '123',
        'Lima',
        '15074',
        'Peru',
        NOW(),
        NOW()
    ),
    (
        3,
        'Luis',
        'Pérez',
        'luis@ejemplo.com',
        'Calle Los Pinos',
        '789',
        'Lima',
        '15036',
        'Peru',
        NOW(),
        NOW()
    );
-- =========================================================================
-- 3. LAVANDERÍAS -> 3 Locales
-- =========================================================================
INSERT INTO laundries (
        id,
        name,
        address,
        rating,
        image_url,
        status,
        created_at,
        updated_at
    )
VALUES (
        1,
        'CleanWave Central (Miraflores)',
        'Av. Larco 456, Miraflores',
        4.9,
        'https://images.unsplash.com/photo-1545173168-9f1947eebb7f',
        'OPEN',
        NOW(),
        NOW()
    ),
    (
        2,
        'CleanWave Norte (Los Olivos)',
        'Av. Antunez de Mayolo 123',
        4.5,
        'https://images.unsplash.com/photo-1517677208171-0bc6725a3e60',
        'OPEN',
        NOW(),
        NOW()
    ),
    (
        3,
        'EcoWash Surco',
        'Av. Encalada 789, Surco',
        4.7,
        'https://images.unsplash.com/photo-1582735689369-4fe89db7114c',
        'OPEN',
        NOW(),
        NOW()
    );
-- =========================================================================
-- 4. PLANES 
-- =========================================================================
INSERT INTO plans (
        id,
        name,
        price,
        type,
        billing_period,
        laundry_features,
        client_features,
        recommended,
        created_at,
        updated_at
    )
VALUES (
        1,
        'Plan Gratuito',
        0.00,
        'FREE',
        'MONTHLY',
        '["Hasta 50 pedidos al mes", "Gestión básica de tickets"]',
        '["App móvil gratuita", "Historial de pedidos", "Soporte estándar"]',
        0,
        NOW(),
        NOW()
    ),
    (
        2,
        'Plan Premium',
        89.00,
        'PREMIUM',
        'MONTHLY',
        '["Pedidos ilimitados", "Rutas optimizadas con IA", "Notificaciones automáticas", "Soporte prioritario 24/7"]',
        '["Tracking en vivo", "Envíos gratis ilimitados", "Ofertas exclusivas"]',
        1,
        NOW(),
        NOW()
    ),
    (
        3,
        'Plan Anual',
        1040.00,
        'ANNUAL',
        'YEARLY',
        '["Todo lo del Plan Premium", "Ahorro de dos meses", "Dashboard analítico avanzado", "Asesor de marketing"]',
        '["Tracking en vivo", "Envíos gratis ilimitados", "Regalos por fidelidad"]',
        0,
        NOW(),
        NOW()
    );
-- =========================================================================
-- 5. SUSCRIPCIONES
-- =========================================================================
INSERT INTO subscriptions (
        id,
        plan_id,
        laundry_id,
        status,
        start_date,
        end_date,
        created_at,
        updated_at
    )
VALUES (
        1,
        2,
        1,
        'ACTIVE',
        '2026-01-01',
        '2026-12-31',
        NOW(),
        NOW()
    ),
    (
        2,
        1,
        2,
        'ACTIVE',
        '2026-03-15',
        '2026-04-15',
        NOW(),
        NOW()
    ),
    (
        3,
        3,
        3,
        'ACTIVE',
        '2026-06-01',
        '2027-06-01',
        NOW(),
        NOW()
    );
-- =========================================================================
-- 6. PEDIDOS -> 10 Pedidos (Repartidos entre Andrea y Luis)
-- =========================================================================
INSERT INTO orders (
        id,
        user_id,
        laundry_id,
        status,
        address,
        scheduled_pickup,
        notes,
        created_at,
        updated_at
    )
VALUES (
        101,
        2,
        1,
        'DELIVERED',
        'Av. Larco 123',
        '2026-06-01',
        'Llamar al llegar',
        '2026-06-01',
        '2026-06-03'
    ),
    (
        102,
        2,
        1,
        'IN_PROCESS',
        'Av. Larco 123',
        '2026-06-18',
        'Cuidado con ropa blanca',
        NOW(),
        NOW()
    ),
    (
        103,
        3,
        1,
        'PENDING',
        'Calle Los Pinos 789',
        '2026-06-20',
        'Dejar en recepción',
        NOW(),
        NOW()
    ),
    (
        104,
        2,
        2,
        'DELIVERED',
        'Av. Larco 123',
        '2026-06-10',
        'Sin suavizante',
        '2026-06-10',
        '2026-06-12'
    ),
    (
        105,
        3,
        1,
        'IN_PROCESS',
        'Calle Los Pinos 789',
        '2026-06-17',
        NULL,
        NOW(),
        NOW()
    ),
    (
        106,
        2,
        3,
        'PENDING',
        'Av. Larco 123',
        '2026-06-19',
        'Lavar al seco',
        NOW(),
        NOW()
    ),
    (
        107,
        3,
        1,
        'DELIVERED',
        'Calle Los Pinos 789',
        '2026-06-05',
        NULL,
        '2026-06-05',
        '2026-06-07'
    ),
    (
        108,
        2,
        3,
        'IN_PROCESS',
        'Av. Larco 123',
        '2026-06-16',
        'Urgente',
        NOW(),
        NOW()
    ),
    (
        109,
        2,
        1,
        'CANCELLED',
        'Av. Larco 123',
        '2026-06-14',
        'Cliente canceló el pedido',
        NOW(),
        NOW()
    ),
    (
        110,
        3,
        2,
        'DELIVERED',
        'Calle Los Pinos 789',
        '2026-05-20',
        NULL,
        '2026-05-20',
        '2026-05-22'
    );
-- =========================================================================
-- 7. PRENDAS (ORDER ITEMS) -> 16 items
-- =========================================================================
INSERT INTO order_items (
        id,
        order_id,
        garment_type,
        quantity,
        unit_price,
        created_at,
        updated_at
    )
VALUES (1, 101, 'Camisa', 5, 5.00, NOW(), NOW()),
    (2, 101, 'Pantalón', 2, 7.00, NOW(), NOW()),
    (3, 102, 'Vestido', 1, 15.00, NOW(), NOW()),
    (4, 102, 'Saco', 1, 12.00, NOW(), NOW()),
    (5, 103, 'Sábanas', 3, 10.00, NOW(), NOW()),
    (6, 103, 'Funda almohada', 6, 2.00, NOW(), NOW()),
    (7, 104, 'Casaca', 2, 18.00, NOW(), NOW()),
    (8, 105, 'Polo', 10, 3.50, NOW(), NOW()),
    (9, 106, 'Terno Completo', 1, 25.00, NOW(), NOW()),
    (10, 107, 'Jeans', 4, 6.00, NOW(), NOW()),
    (11, 108, 'Cortinas', 2, 30.00, NOW(), NOW()),
    (12, 109, 'Camisa', 1, 5.00, NOW(), NOW()),
    (13, 110, 'Blusa', 4, 6.50, NOW(), NOW());
-- =========================================================================
-- 8. ENTREGAS / LOGÍSTICA -> 10 Entregas
-- =========================================================================
INSERT INTO deliveries (
        id,
        order_id,
        user_id,
        type,
        status,
        address,
        scheduled_date,
        driver_name,
        driver_phone,
        notes,
        created_at,
        updated_at
    )
VALUES (
        1,
        101,
        2,
        'PICKUP',
        'DELIVERED',
        'Av. Larco 123',
        '2026-06-01',
        'Juan Perez',
        '987654321',
        'Puntual',
        NOW(),
        NOW()
    ),
    (
        2,
        101,
        2,
        'DELIVERY',
        'DELIVERED',
        'Av. Larco 123',
        '2026-06-03',
        'Juan Perez',
        '987654321',
        'Todo en orden',
        NOW(),
        NOW()
    ),
    (
        3,
        102,
        2,
        'PICKUP',
        'DELIVERED',
        'Av. Larco 123',
        '2026-06-18',
        'Maria Gomez',
        '987123456',
        'Recogido',
        NOW(),
        NOW()
    ),
    (
        4,
        102,
        2,
        'DELIVERY',
        'PENDING',
        'Av. Larco 123',
        '2026-06-20',
        'Maria Gomez',
        '987123456',
        NULL,
        NOW(),
        NOW()
    ),
    (
        5,
        103,
        3,
        'PICKUP',
        'PENDING',
        'Calle Los Pinos 789',
        '2026-06-20',
        'Luis Torres',
        '999888777',
        'Llamar antes',
        NOW(),
        NOW()
    ),
    (
        6,
        105,
        3,
        'PICKUP',
        'DELIVERED',
        'Calle Los Pinos 789',
        '2026-06-17',
        'Juan Perez',
        '987654321',
        NULL,
        NOW(),
        NOW()
    ),
    (
        7,
        106,
        2,
        'PICKUP',
        'IN_TRANSIT',
        'Av. Larco 123',
        '2026-06-19',
        'Maria Gomez',
        '987123456',
        'En ruta',
        NOW(),
        NOW()
    ),
    (
        8,
        108,
        2,
        'DELIVERY',
        'IN_TRANSIT',
        'Av. Larco 123',
        '2026-06-18',
        'Luis Torres',
        '999888777',
        'Urgente',
        NOW(),
        NOW()
    ),
    (
        9,
        107,
        3,
        'PICKUP',
        'DELIVERED',
        'Calle Los Pinos 789',
        '2026-06-05',
        'Juan Perez',
        '987654321',
        NULL,
        NOW(),
        NOW()
    ),
    (
        10,
        110,
        3,
        'PICKUP',
        'DELIVERED',
        'Calle Los Pinos 789',
        '2026-05-20',
        'Maria Gomez',
        '987123456',
        NULL,
        NOW(),
        NOW()
    );
-- =========================================================================
-- 9. NOTIFICACIONES -> 10 Notificaciones
-- =========================================================================
INSERT INTO notifications (
        id,
        user_id,
        title,
        message,
        type,
        priority,
        is_read,
        link,
        created_at,
        updated_at
    )
VALUES (1, 1, 'Nuevo pedido recibido #103', 'Luis Pérez ha solicitado un recojo en Calle Los Pinos.', 'ORDER', 'HIGH', 0, '/admin/orders/103', NOW(), NOW()),
(2, 1, 'Pedido cancelado #109', 'Andrea Gómez ha cancelado su pedido.', 'SYSTEM', 'HIGH', 1, '/admin/orders/109', NOW(), NOW()),
(3, 1, 'Entrega en curso #108', 'Luis Torres ha iniciado la ruta de entrega.', 'LOGISTICS', 'NORMAL', 0, '/admin/logistics/108', NOW(), NOW()),
(4, 2, 'Tu pedido está en proceso', 'Tu pedido #102 ha sido recogido y ya se está lavando.', 'ORDER', 'NORMAL', 0, '/orders/102', NOW(), NOW()),
(5, 3, 'Recojo programado', 'Tu recojo #103 ha sido programado para el 20 de Junio.', 'LOGISTICS', 'NORMAL', 0, '/orders/103', NOW(), NOW()),
(6, 2, 'Repartidor en camino', 'El repartidor está en camino con tus prendas del pedido #108.', 'LOGISTICS', 'HIGH', 0, '/orders/108', NOW(), NOW()),
(7, 1, 'Nueva Suscripción', 'CleanWave Central ha renovado su suscripción al Plan Premium.', 'SYSTEM', 'NORMAL', 1, '/admin/billing/1', NOW(), NOW()),
(8, 1, 'Pedido Retrasado #105', 'El pedido 105 lleva más de 24h en estado IN_PROCESS.', 'ORDER', 'HIGH', 0, '/admin/orders/105', NOW(), NOW()),
(9, 1, 'Alerta de Logística', 'El conductor Juan Perez reportó tráfico intenso en Miraflores.', 'LOGISTICS', 'NORMAL', 0, '/admin/logistics', NOW(), NOW()),
(10, 3, 'Tu pedido fue entregado', 'El conductor dejó tu pedido #110 en recepción.', 'ORDER', 'NORMAL', 1, '/orders/110', NOW(), NOW());
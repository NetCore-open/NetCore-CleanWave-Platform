package com.netcore.cleanwave.platform.billing.infrastructure.seeding;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Plan;
import com.netcore.cleanwave.platform.billing.domain.model.valueobjects.PlanType;
import com.netcore.cleanwave.platform.billing.domain.repositories.PlanRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Database seeder that pre-populates the subscription plans on application startup.
 *
 * <p>Ensures that "Plan Gratuito", "Plan Premium", and "Plan Anual" are registered
 * in the database to align with frontend expectations.</p>
 */
@NullMarked
@Component
public class PlanDatabaseSeeder implements CommandLineRunner {

    private final PlanRepository planRepository;

    public PlanDatabaseSeeder(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public void run(String... args) {
        if (planRepository.findAll().isEmpty()) {
            // Seed Plan Gratuito (FREE)
            var freePlan = new Plan(
                    "Plan Gratuito",
                    0.0,
                    PlanType.FREE,
                    "siempre",
                    List.of("Hasta 50 pedidos al mes", "Gestión básica de tickets"),
                    List.of("App móvil gratuita", "Historial de pedidos"),
                    false
            );
            planRepository.save(freePlan);

            // Seed Plan Premium (PREMIUM)
            var premiumPlan = new Plan(
                    "Plan Premium",
                    89.0,
                    PlanType.PREMIUM,
                    "mes",
                    List.of("Pedidos ilimitados", "Rutas optimizadas con IA", "Notificaciones automáticas"),
                    List.of("Tracking en vivo", "Soporte prioritario"),
                    true
            );
            planRepository.save(premiumPlan);

            // Seed Plan Anual (ANNUAL)
            var annualPlan = new Plan(
                    "Plan Anual",
                    1040.0,
                    PlanType.ANNUAL,
                    "año",
                    List.of("Acceso completo por 12 meses", "Rutas optimizadas con IA", "Notificaciones automáticas"),
                    List.of("Tracking en vivo", "Soporte prioritario"),
                    false
            );
            planRepository.save(annualPlan);
        }
    }
}

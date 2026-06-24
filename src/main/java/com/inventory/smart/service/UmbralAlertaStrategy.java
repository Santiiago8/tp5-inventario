package com.inventory.smart.service;

import com.inventory.smart.config.StockConfig;
import com.inventory.smart.model.NivelAlerta;
import com.inventory.smart.model.Producto;
import org.springframework.stereotype.Component;

/**
 * Estrategia de alerta basada en umbrales fijos de stock minimo y critico,
 * configurados externamente en application.yml.
 *
 * @author Grupo
 * @since 1.0
 */
@Component
public class UmbralAlertaStrategy implements AlertaStrategy {

    private final StockConfig stockConfig;

    /**
     * Crea la estrategia inyectando la configuracion de umbrales.
     *
     * @param stockConfig configuracion con los umbrales minimo y critico
     */
    public UmbralAlertaStrategy(StockConfig stockConfig) {
        this.stockConfig = stockConfig;
    }

    /** {@inheritDoc} */
    @Override
    public NivelAlerta evaluar(Producto producto) {
        int stock = producto.getStock();
        if (stock <= stockConfig.getCritico()) {
            return NivelAlerta.CRITICO;
        }
        if (stock <= stockConfig.getMinimo()) {
            return NivelAlerta.BAJO;
        }
        return NivelAlerta.NORMAL;
    }
}
package com.inventory.smart.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de los umbrales de stock leidos desde application.yml,
 * bajo el prefijo inventario.stock.
 *
 * @author Grupo
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "inventario.stock")
public class StockConfig {

    private int minimo;
    private int critico;

    /** @return el umbral minimo de stock antes de alerta BAJO */
    public int getMinimo() { return minimo; }

    /** @param minimo el umbral minimo a asignar */
    public void setMinimo(int minimo) { this.minimo = minimo; }

    /** @return el umbral critico de stock antes de alerta CRITICO */
    public int getCritico() { return critico; }

    /** @param critico el umbral critico a asignar */
    public void setCritico(int critico) { this.critico = critico; }
}
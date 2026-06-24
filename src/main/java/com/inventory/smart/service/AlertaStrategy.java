package com.inventory.smart.service;

import com.inventory.smart.model.NivelAlerta;
import com.inventory.smart.model.Producto;

/**
 * Estrategia para determinar el nivel de alerta de stock de un producto.
 *
 * <p>Aplica el patron Strategy: cada implementacion encapsula una regla
 * distinta de evaluacion. Esto permite agregar nuevas reglas de alerta
 * en el futuro sin modificar el codigo existente (principio OCP).</p>
 *
 * @author Grupo
 * @since 1.0
 */
public interface AlertaStrategy {

    /**
     * Evalua el nivel de alerta correspondiente al stock actual de un producto.
     *
     * @param producto producto a evaluar
     * @return el nivel de alerta calculado
     */
    NivelAlerta evaluar(Producto producto);
}
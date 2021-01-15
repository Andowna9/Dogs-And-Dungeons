package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;


// Clase para gestionar los efectos de partículas eficientemente

public class ParticleEffectsManager {

    // Tipo de efecto

    public enum EffectType {

        ENEMY_DEATH, ATTACK_UPGRADE, SPEED_UPGRADE
    }


    // Contenedores de efectos (para reducir la carga debida al garbage collector -> Se reciclan efectos)

    private HashMap<EffectType, ParticleEffectPool> effectPools;

    // Efectos que se renderizan y una vez terminan se devuelven a su respectivo contenedor

    Array<ParticleEffectPool.PooledEffect> effects = new Array<>();

    public ParticleEffectsManager() {

        effectPools = new HashMap<>();

        // Cargamos el atlas con las imágenes correspondientes a las partículas

        TextureAtlas particleAtlas = new TextureAtlas(Gdx.files.internal("effects/enemy_death/particles.atlas"));

        // Efecto de muerte de enemigos

        ParticleEffect enemyDeathEffect = new ParticleEffect();

        enemyDeathEffect.load(Gdx.files.internal("effects/enemy_death/enemyDeath.p"), particleAtlas);

        storeEffect(enemyDeathEffect, EffectType.ENEMY_DEATH);


        // Efecto de aumento de ataque

        ParticleEffect attackUpgradeEffect = new ParticleEffect();

        attackUpgradeEffect.load(Gdx.files.internal("effects/player/attack_upgrade.p"), Gdx.files.internal("HUD"));

        storeEffect(attackUpgradeEffect, EffectType.ATTACK_UPGRADE);

        // Efecto de aumento de velocidad

        ParticleEffect speedUpgradeEffect = new ParticleEffect();

        speedUpgradeEffect.load(Gdx.files.internal("effects/player/speed_upgrade.p"), Gdx.files.internal("HUD"));

        storeEffect(speedUpgradeEffect, EffectType.SPEED_UPGRADE);

    }

    // Método para cargar un nuevo efecto y asignarle una pool

    private void storeEffect(ParticleEffect effect, EffectType type) {

        ParticleEffectPool pool = new ParticleEffectPool(effect,1,2);

        effectPools.put(type,pool);

    }

    // Método para generar un efecto del tipo especificado

    public void generateEffect(float x, float y, EffectType type) {

        ParticleEffectPool.PooledEffect effect = effectPools.get(type).obtain();

        effect.setPosition(x, y);

        effects.add(effect);
    }

    // Renderizado - Actualización de efectos

    void renderEffects(Batch batch, float delta) {

        // Recorremos el array al revés para que el borrado de un elemento no altere el próximo índice

        for (int i = effects.size - 1; i >= 0; i--) {

            ParticleEffectPool.PooledEffect effect = effects.get(i);

            effect.draw(batch, delta);

            if (effect.isComplete()) {

                effect.free();

                effects.removeIndex(i);
            }

        }
    }
}

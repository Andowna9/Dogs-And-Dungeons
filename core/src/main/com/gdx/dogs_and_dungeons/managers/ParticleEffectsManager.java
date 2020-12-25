package com.gdx.dogs_and_dungeons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;


// Clase para gestionar los efectos de partículas eficientemente

public class ParticleEffectsManager {

    // Tipo de efecto

    public enum EffectType {

        ENEMY_DEATH
    }

    // Atlas

    TextureAtlas particleAtlas;

    // Efectos

    ParticleEffect enemyDeathEffect;

    // Contenedores de efectos (para reducir la carga debida al garbage collector -> Se reciclan efectos)

    ParticleEffectPool enemyDeathPool;

    // Efectos que se renderizan y una vez terminan se devuelven a su respectivo contenedor

    Array<ParticleEffectPool.PooledEffect> effects = new Array<>();

    public ParticleEffectsManager() {

        // Cargamos el atlas con las imágenes correspondientes a las partículas

        particleAtlas = new TextureAtlas(Gdx.files.internal("effects/enemy_death/particles.atlas"));

        // Efecto de muerte de enemigos

        enemyDeathEffect = new ParticleEffect();

        enemyDeathEffect.load(Gdx.files.internal("effects/enemy_death/enemyDeath.p"), particleAtlas);

        // Contenedor

        enemyDeathPool = new ParticleEffectPool(enemyDeathEffect,1, 2);

    }

    // Devuelve el contenedor de efectos de acuerdo con el tipo

    private ParticleEffectPool getPool(EffectType type) {

        ParticleEffectPool pool = null;

        // Próximamente más efectos ...

        switch (type) {

            case ENEMY_DEATH:

                pool = enemyDeathPool;

                break;
        }

        return pool;
    }

    // Método para generar un efecto del tipo especificado

    public void generateEffect(float x, float y, EffectType type) {

        ParticleEffectPool.PooledEffect effect = getPool(type).obtain();

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

package com.simplycmd.featherlib.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.Getter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Render {
    RenderLayer layer();

    public enum RenderLayer {
        SOLID(net.minecraft.client.render.RenderLayer.getSolid()),
        CUTOUT_MIPPED(net.minecraft.client.render.RenderLayer.getCutoutMipped()),
        CUTOUT(net.minecraft.client.render.RenderLayer.getCutout()),
        TRANSLUCENT(net.minecraft.client.render.RenderLayer.getTranslucent()),
        TRANSLUCENT_MOVING_BLOCK(net.minecraft.client.render.RenderLayer.getTranslucentMovingBlock()),
        TRANSLUCENT_NO_CRUMBLING(net.minecraft.client.render.RenderLayer.getTranslucentNoCrumbling()),
        LEASH(net.minecraft.client.render.RenderLayer.getLeash()),
        WATER_MASK(net.minecraft.client.render.RenderLayer.getWaterMask()),
        ARMOR_GLINT(net.minecraft.client.render.RenderLayer.getArmorGlint()),
        ARMOR_ENTITY_GLINT(net.minecraft.client.render.RenderLayer.getArmorEntityGlint()),
        GLINT_TRANSLUCENT(net.minecraft.client.render.RenderLayer.getGlintTranslucent()),
        GLINT(net.minecraft.client.render.RenderLayer.getGlint()),
        DIRECT_GLINT(net.minecraft.client.render.RenderLayer.getDirectGlint()),
        ENTITY_GLINT(net.minecraft.client.render.RenderLayer.getEntityGlint()),
        DIRECT_ENTITY_GLINT(net.minecraft.client.render.RenderLayer.getDirectEntityGlint()),
        LIGHTNING(net.minecraft.client.render.RenderLayer.getLightning()),
        TRIPWIRE(net.minecraft.client.render.RenderLayer.getTripwire()),
        END_PORTAL(net.minecraft.client.render.RenderLayer.getEndPortal()),
        END_GATEWAY(net.minecraft.client.render.RenderLayer.getEndGateway());

        @Getter
        private final net.minecraft.client.render.RenderLayer layer;

        RenderLayer(net.minecraft.client.render.RenderLayer layer) {
            this.layer = layer;
        }
    }
}

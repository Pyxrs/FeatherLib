package com.simplycmd.featherlib.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

/**
 * Simplified version of {@link net.minecraft.client.render.entity.EntityRenderer}
 */
@Environment(EnvType.CLIENT)
public abstract class BaseEntityRenderer extends EntityRenderer<Entity> {
    private static Identifier TEXTURE;
    private final EntityModel<Entity> model;

    public BaseEntityRenderer(Identifier id, EntityModel<Entity> model, EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = model;
        TEXTURE = new Identifier(id.getNamespace(), id.getPath());
    }

    public void render(Entity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        this.model.setAngles(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return TEXTURE;
    }
}

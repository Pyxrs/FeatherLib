package com.simplycmd.featherlib.registry;

import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.Optional;

import com.simplycmd.featherlib.util.Tuple.Bi;

import lombok.Getter;

public class RegistryType<T> {
    private @Getter Class<T> generic;
    private @Getter Optional<TypeLang> lang;
    private @Getter Optional<TypeRender> render;
    private @Getter TypeRegister register;

    public RegistryType(Builder<T> builder) {
        generic = builder.generic;
        lang = builder.lang;
        render = builder.render;
        register = builder.register;
    }

    // Nasty workaround because Java handles generics weirdly
    public interface TypeLang { JLang accept(Bi<Identifier, Object> object, JLang lang, String translation); }
    @FunctionalInterface public interface TypeLangG<T> { JLang accept(Bi<Identifier, T> object, JLang lang, String translation); }
    
    public interface TypeRender { void accept(Bi<Identifier, Object> object, BlockRenderLayerMap layerMap, RenderLayer layer); }
    @FunctionalInterface public interface TypeRenderG<T> { void accept(Bi<Identifier, T> object, BlockRenderLayerMap layerMap, RenderLayer layer); }

    public interface TypeRegister { void accept(Bi<Identifier, Object> object, RegistryHandler registry); }
    @FunctionalInterface public interface TypeRegisterG<T> { void accept(Bi<Identifier, T> object, RegistryHandler registry); }

    public static <T> Builder<T> builder(Class<T> type) {
        return new Builder<T>(type);
    }

    public static class Builder<T> {
        private Class<T> generic;
        private Optional<TypeLang> lang = Optional.empty();
        private Optional<TypeRender> render = Optional.empty();
        private TypeRegister register;

        private Builder(Class<T> generic) {
            this.generic = generic;
        }

        public Builder<T> lang(TypeLangG<T> lang) {
            this.lang = Optional.of(new TypeLang() {
                @Override
                public JLang accept(Bi<Identifier, Object> object, JLang lang2, String translation) {
                    return lang.accept(new Bi<Identifier, T>(object.a, generic.cast(object.b)), lang2, translation);
                }
            });
            return this;
        }

        public Builder<T> render(TypeRenderG<T> render) {
            this.render = Optional.of(new TypeRender() {
                @Override
                public void accept(Bi<Identifier, Object> object, BlockRenderLayerMap layerMap, RenderLayer layer) {
                    render.accept(new Bi<Identifier, T>(object.a, generic.cast(object.b)), layerMap, layer);
                }
            });
            return this;
        }

        public RegistryType<T> build(TypeRegisterG<T> register) {
            this.register = new TypeRegister() {
                @Override
                public void accept(Bi<Identifier, Object> object, RegistryHandler registry) {
                    register.accept(new Bi<Identifier, T>(object.a, generic.cast(object.b)), registry);
                }
            };
            return new RegistryType<T>(this);
        }
    }
}

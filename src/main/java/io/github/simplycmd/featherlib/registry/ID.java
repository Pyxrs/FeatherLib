package io.github.simplycmd.featherlib.registry;

import lombok.Getter;
import net.minecraft.util.Identifier;

public class ID {
    @Getter
    private final Identifier identifier;

    @Getter
    private final String namespace;

    @Getter
    private final String id;

    public ID(String namespace, String id) {
        this.namespace = namespace;
        this.id = id;
        identifier = new Identifier(namespace, id);
    }
}

package it.jakegblp.lusk.elements.minecraft.entities.llama.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.jetbrains.annotations.NotNull;

import static it.jakegblp.lusk.utils.Constants.PAPER_HAS_1_19_2_EXTENDED_ENTITY_API;

@Name("Llama - Caravan Head")
@Description("Returns the caravan head of a llama.")
@Examples({"broadcast caravan head of target"})
@Since("1.0.3")
@RequiredPlugins("Paper 1.19.2+")
@SuppressWarnings("unused")
public class ExprLlamaCaravanHead extends SimplePropertyExpression<LivingEntity, LivingEntity> {
    static {
        if (PAPER_HAS_1_19_2_EXTENDED_ENTITY_API)
            register(ExprLlamaCaravanHead.class, LivingEntity.class, "[llama] caravan head", "livingentities");
    }

    @Override
    public @NotNull Class<? extends LivingEntity> getReturnType() {
        return LivingEntity.class;
    }

    @Override
    public LivingEntity convert(LivingEntity e) {
        if (e instanceof Llama llama) return llama.getCaravanHead();
        return null;
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "llama caravan head";
    }
}
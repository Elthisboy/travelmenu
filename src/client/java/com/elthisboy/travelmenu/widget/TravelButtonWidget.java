package com.elthisboy.travelmenu.client.screen.widget;

import com.elthisboy.travelmenu.client.screen.TravelClientActions;
import com.elthisboy.travelmenu.economy.MoneyHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class TravelButtonWidget extends ButtonWidget {

    private final int cost;
    private final String destination;
    private final Text  description;
    private final ItemStack iconStack;

    public TravelButtonWidget(
            int x,
            int y,
            int width,
            int height,
            Text message,
            String destination,
            int cost,
            Text description,
            String iconId
    ) {
        super(x, y, width, height, message, button -> {

            System.out.println("CLICK TRAVEL BUTTON: " + destination);

            TravelClientActions.requestTeleport(destination);

            // cerrar menú
            MinecraftClient.getInstance().setScreen(null);

        }, DEFAULT_NARRATION_SUPPLIER);

        this.destination = destination;
        this.cost = cost;
        this.description = description;

        Item iconItem = Registries.ITEM.get(Identifier.of(iconId));
        this.iconStack = new ItemStack(iconItem);
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;
        int money = MoneyHelper.getMoney(net.minecraft.client.MinecraftClient.getInstance().player);
        boolean canAfford = money >= cost;

        this.active = canAfford;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {

        super.renderWidget(context, mouseX, mouseY, delta);

        MinecraftClient client = MinecraftClient.getInstance();

        // Render del icono del item
        context.drawItem(
                iconStack,
                this.getX() + 4,
                this.getY() + 2
        );

        // Tooltip
        if (this.isHovered()) {

            List<Text> tooltip = new ArrayList<>();

            tooltip.add(this.getMessage());
            tooltip.add(description);
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("travel.tooltip.cost", cost));

            if (!this.active) {
                tooltip.add(Text.translatable("travel.tooltip.nomoney"));
            } else {
                tooltip.add(Text.translatable("travel.tooltip.click"));
            }

            context.drawTooltip(
                    client.textRenderer,
                    tooltip,
                    mouseX,
                    mouseY
            );
        }
    }
}
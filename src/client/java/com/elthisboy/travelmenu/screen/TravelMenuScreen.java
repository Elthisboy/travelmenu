package com.elthisboy.travelmenu.client.screen;

import com.elthisboy.travelmenu.client.screen.widget.TravelButtonWidget;
import com.elthisboy.travelmenu.config.TravelConfigManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TravelMenuScreen extends Screen {

    private int centerX;
    private int startY;
    private int page = 0;
    private static final int DESTINATIONS_PER_PAGE = 5;
    public TravelMenuScreen() {
        super(Text.translatable("travel.menu.title"));


    }



    @Override
    protected void init() {

        centerX = this.width / 2;
        startY = this.height / 2 - 60;


        int index = 0;

        int start = page * DESTINATIONS_PER_PAGE;
        int end = Math.min(
                start + DESTINATIONS_PER_PAGE,
                TravelConfigManager.destinations.size()
        );

        for (int i = start; i < end; i++) {

            var dest = TravelConfigManager.destinations.get(i);

            int y = startY + ((i - start) * 24);

            this.addDrawableChild(
                    new TravelButtonWidget(
                            centerX - 100,
                            y,
                            200,
                            20,
                            Text.translatable("travel.destination." + dest.id),
                            dest.id,
                            dest.cost,
                            Text.translatable("travel.destination." + dest.id + ".desc"),
                            dest.icon
                    )
            );
        }


        // BOTÓN CERRAR
        addDrawableChild(
                ButtonWidget.builder(
                        Text.translatable("travel.menu.close"),
                        button -> this.close()
                ).dimensions(
                        centerX - 40,
                        startY + 110,
                        80,
                        20
                ).build()
        );


        // PREVIOUS
        boolean hasPrev = page > 0;
        boolean hasNext = (page + 1) * DESTINATIONS_PER_PAGE < TravelConfigManager.destinations.size();

        ButtonWidget prevButton = ButtonWidget.builder(
                Text.literal("<"),
                b -> {
                    page--;
                    clearAndInit();
                }
        ).dimensions(centerX - 120, startY + 130, 20, 20).build();

        prevButton.active = hasPrev;

        addDrawableChild(prevButton);

// NEXT
        ButtonWidget nextButton = ButtonWidget.builder(
                Text.literal(">"),
                b -> {
                    page++;
                    clearAndInit();
                }
        ).dimensions(centerX + 100, startY + 130, 20, 20).build();

        nextButton.active = hasNext;

        addDrawableChild(nextButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        int panelX = width / 2 - 120;
        int panelY = height / 2 - 80;
        int panelW = 240;
        int panelH = 160;

        context.fill(panelX, panelY, panelX + panelW, panelY + panelH, 0xCC1E1E1E);
        context.drawBorder(panelX, panelY, panelW, panelH, 0xFFFFFFFF);

        context.drawCenteredTextWithShadow(
                textRenderer,
                title,
                width / 2,
                panelY + 10,
                0xFFFFFF
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // NO dibujar el fondo borroso
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
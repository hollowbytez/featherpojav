# ✦ HollowClient - Architecture Map ✦

This map walks through every package, subfolder, and Java file inside HollowClient, explaining exactly what they do, how they work, and how they connect to Minecraft.

---

## ✧ Package: `net.hollowclient.client`

### 1. `HollowClient.java`
*   **Role**: Entrypoint (`ClientModInitializer`) of the client.
*   **Responsibility**: 
    *   Fires when the Fabric mod loader initializes.
    *   Loads local configurations (`HollowConfig.load()` and `HollowHudConfig.load()`).
    *   Performs initial setup (e.g., loading custom client properties).

---

## ✧ Package: `net.hollowclient.client.config`

### 2. `HollowConfig.java`
*   **Role**: Core Client Configuration.
*   **Responsibility**:
    *   Saves and loads general settings like `autoClicker`, `autoClickerMinCPS`, `autoClickerMaxCPS`, `fullbright`, and `dragonWings`.
    *   Serializes values to `config.json` inside the config folder using GSON.

### 3. `HollowHudConfig.java`
*   **Role**: HUD Layout Configuration.
*   **Responsibility**:
    *   Keeps track of where each HUD widget is placed on screen.
    *   Stores `enabled` (boolean), `x` (float), `y` (float), and `scale` (float) for every HUD module.
    *   Serializes values to `hud_config.json` using GSON.

---

## ✧ Package: `net.hollowclient.client.gui`

This package handles all client screens, rendering utilities, and layout editors.

### 4. `HollowHomeScreen.java`
*   **Role**: Custom Title/Main Menu Screen.
*   **Responsibility**:
    *   Draws the custom 88-frame loop animation (`1.png` to `88.png`) at 25 FPS.
    *   Renders a custom neon pink/red gradient and soft-drifting embers.
    *   Draws the "HOLLOW CLIENT" title with chromatic aberration offset shading (magenta/cyan shadows).
    *   Draws sleek, custom buttons (Singleplayer, Multiplayer, Screenshots, Hollow Settings, Quit Game).
    *   Renders active player avatar face + username top-right, quick-join server shortcuts left.

### 5. `HollowHudEditorScreen.java`
*   **Role**: Interactive HUD Drag-and-Drop Layout Editor.
*   **Responsibility**:
    *   Triggered in-game by pressing **Right Shift**.
    *   Renders semi-transparent bounds around all active HUD elements.
    *   Allows players to left-click and drag elements to new locations on screen.
    *   Saves the layout configurations instantly to disk upon closing.

### 6. `HollowSettingsScreen.java`
*   **Role**: Client Configuration Dashboard.
*   **Responsibility**:
    *   A clean multi-card settings menu.
    *   Allows toggling HUD elements on/off, adjusting parameters (CPS, colors, etc.).
    *   Saves all variables dynamically to config files.

### 7. `RenderUtils.java`
*   **Role**: Graphics Helper Library.
*   **Responsibility**:
    *   Uses Signed-Distance-Field (SDF) mathematics to render perfectly smooth anti-aliased rectangles (`drawRoundedRect`) and outlines (`drawRoundedOutline`).
    *   Prevents aliasing (pixel jaggedness) when drawing custom HUD containers, buttons, and panels.

---

## ✧ Package: `net.hollowclient.client.hud`

### 8. `HollowHudRenderer.java`
*   **Role**: Main In-Game HUD Render Engine.
*   **Responsibility**:
    *   Listens to the overlay render tick of Minecraft.
    *   Iterates through all registered HUD elements (Ping, FPS, CPS, Keystrokes, Coordinates, Potion HUD, TargetHUD, etc.).
    *   If a module is enabled, it grabs its scale and position, translates the OpenGL matrix, and draws the custom design (text, backgrounds, status bars).

---

## ✧ Package: `net.hollowclient.mixin.client`

Mixins are hook points that inject code directly into Minecraft's own source code at runtime.

### 9. `MinecraftClientMixin.java`
*   **Hooks**: `MinecraftClient` class.
*   **Responsibility**:
    *   Forces the startup window clear-color to black (`0.0F, 0.0F, 0.0F, 1.0F`) to eliminate the white screen flash.
    *   Intercepts `setScreen` calls to replace the vanilla `TitleScreen` with `HollowHomeScreen`.
    *   Runs the ticking logic for the built-in random CPS AutoClicker.

### 10. `SplashOverlayMixin.java`
*   **Hooks**: `SplashOverlay` (Mojang Loading Screen).
*   **Responsibility**:
    *   Cancels Mojang's default rendering engine.
    *   Force-paints a solid black background on every frame to prevent any trailing white windows.
    *   Fades in and rotates the custom client logo in the center of the screen with a smooth ease-in-out curve.
    *   Ensures clean handover to the title screen and prevents fullscreen toggling bugs.

### 11. `InGameHudMixin.java`
*   **Hooks**: `InGameHud` (Ingame overlays).
*   **Responsibility**:
    *   Hooks right after the hotbar renders to execute `HollowHudRenderer.renderHUD()`.

### 12. `KeyboardMixin.java`
*   **Hooks**: `Keyboard` class.
*   **Responsibility**:
    *   Listens for keystrokes.
    *   If **Right Shift** is pressed, it opens the `HollowHudEditorScreen`.

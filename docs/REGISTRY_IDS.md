# ✦ HollowClient - Registry IDs & Database ✦

Here is the absolute complete list of every internal identifier, configuration key, folder path, keybind, and mixin connection used in the client.

---

## ✧ HUD Module Identifiers (Used in `HollowHudConfig`)
These keys are passed to `HollowHudConfig.get("ID")` to save or retrieve their on-screen coordinates (`x`, `y`), scale, and whether they are active (`enabled`).

| HUD ID | Function | Render Format |
| :--- | :--- | :--- |
| **`Keystrokes`** | Displays WASD, LMB/RMB status + real-time CPS. | Grid layout (W/A/S/D/LMB/RMB/Space) |
| **`Coordinates`** | Renders current X, Y, Z + Direction facing. | Boxed double-line text |
| **`FPS HUD`** | Displays real-time FPS count. | Boxed single-line text |
| **`Direction HUD`** | Compass scale displaying heading degrees. | Horizontal sliding ruler tape |
| **`Armor HUD`** | Renders currently equipped armor + durability status. | Linear grid (Horizontal or Vertical) |
| **`Potion HUD`** | Active status effects + remaining duration. | Boxed vertical stack |
| **`Armor Bar`** | Renders active armor value text over hotbar. | `🛡 [value]` |
| **`Hearts`** | Numerical health + absorption points. | `❤ HP/Max + [Abs]★` |
| **`Pack Display`** | Displays name of the active resource pack. | Boxed text label |
| **`Combo Display`** | Tracking combo hits on opponents. | `Combo: [count]` |
| **`Ping Display`** | Current server response time. | `Ping: [ms]ms` |
| **`Playtime`** | Tracks current session stopwatch time. | `Time: HH:MM:SS` |
| **`Reach Display`** | Displays the distance of your last hit. | `Reach: [meters]m` |
| **`Server Address`** | IP of the server you are connected to. | `IP: [address]` |
| **`Speed Meter`** | Horizontal speed meter in blocks/sec. | `Speed: [blocks/s] m/s` |
| **`Stopwatch`** | Custom timer utility. | `Timer: MM:SS.ms` |
| **`Item Counter`** | Counts items matching whatever is in your hand. | `Held: [quantity]` |
| **`Target HUD`** | Displays entity name + current health bar of target. | Horizontal container card |

---

## ✧ Global Settings (Used in `HollowConfig`)
These fields are parsed into the local `config.json` file.

| Config Field | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `autoClicker` | `boolean` | `false` | Toggles the built-in combat AutoClicker. |
| `autoClickerMinCPS` | `int` | `8` | Minimum clicks per second generated. |
| `autoClickerMaxCPS` | `int` | `15` | Maximum clicks per second generated. |
| `fullbright` | `boolean` | `true` | Overrides ambient lightmap settings for night vision. |
| `dragonWings` | `boolean` | `true` | Renders the 3D custom wings cosmetic model on the player. |
| `armorHUDVertical` | `boolean` | `false` | Arranges the Armor HUD vertically if true, horizontally if false. |
| `armorStatus` | `boolean` | `true` | Displays exact durability numbers beside armor icons. |

---

## ✧ Mixin Registry
Every mixin file in `net.hollowclient.mixin.client` maps directly to a target class inside Minecraft's source code. Refer to this table when debugging conflicts:

| Mixin Class | Target Minecraft Class | Injected Method | Purpose |
| :--- | :--- | :--- | :--- |
| `MinecraftClientMixin` | `net.minecraft.client.MinecraftClient` | `<init>`, `setScreen`, `tick` | Handles clear colors, title screen redirection, AutoClicker ticking. |
| `SplashOverlayMixin` | `net.minecraft.client.gui.screen.SplashOverlay` | `render` | Entirely overrides loading screen, rendering black background and custom spinning logo. |
| `InGameHudMixin` | `net.minecraft.client.gui.hud.InGameHud` | `render` | Overrides game HUD drawing to draw our custom `HollowHudRenderer` elements. |
| `KeyboardMixin` | `net.minecraft.client.Keyboard` | `onKey` | Listens for Right Shift to launch HUD Editor. |
| `GameMenuScreenMixin` | `net.minecraft.client.gui.screen.GameMenuScreen` | `init` | Redirects vanilla pause menu to `HollowGameMenuScreen`. |
| `PlayerEntityRendererMixin`| `net.minecraft.client.render.entity.PlayerEntityRenderer`| `init` | Registers custom cosmetics renderers (e.g. Dragon Wings). |
| `LightmapTextureManagerMixin`| `net.minecraft.client.render.LightmapTextureManager`| `update` | Injects Fullbright (gamma) override values. |

---

## ✧ Resources & Assets Registry
All custom client assets are located under the namespace resource path: `A:\MCMDS\src\main\resources\assets\hollowclient\`

- **Custom Font**:
  - `hollowclient:font/eternalo.otf`
  - Font Identifier: `hollowclient:eternalo`
- **Menu Animation Background**:
  - Location: `assets/hollowclient/textures/gui/animated_menu/`
  - Sequential files: `1.png` through `88.png` (Optimized 480p PNG images).
- **Loading screen Logo**:
  - Location: `assets/hollowclient/textures/gui/loading_logo.png` (Custom rotating image during boot).

# ✦ HollowClient - Overview ✦

HollowClient is a custom, performance-focused competitive client for **Minecraft 1.21.1 (Fabric)**. It is built to offer premium visual aesthetics, competitive PvP tracking, and extensive HUD customization.

---

## ✧ Folder Structure at a Glance
Here is the core structure of the client repository. When building or modifying, keep this file map in mind:

```
A:\MCMDS
├── build.gradle                 # Fabric build scripts & dependencies
├── logo.png                     # Client logo used on GitHub/UI
├── README.md                    # Public download/landing page
├── hollowclient-1.0.0.jar       # Distribution build of the client
├── docs/                        # Complete codebase documentation
│   ├── OVERVIEW.md              # [You are here] High-level overview
│   ├── ARCHITECTURE_MAP.md      # Flow diagrams and package details
│   ├── REGISTRY_IDS.md          # Comprehensive config/HUD/mixin registries
│   └── AI_GRAPH_PROMPT.txt      # Text prompt to generate visual graphs
└── src/
    └── main/
        ├── java/net/hollowclient/
        │   ├── client/          # Core Client Codebase
        │   │   ├── config/      # Config handlers (Save/Load)
        │   │   ├── gui/         # Custom screens & SDF renderers
        │   │   ├── hud/         # HUD element rendering loop
        │   │   └── render/      # Specialized renderer attachments (Cosmetics)
        │   └── mixin/client/    # Mixin hooks injecting into Minecraft core
        └── resources/
            └── assets/hollowclient/
                ├── font/        # Custom typography (eternalo)
                └── textures/    # Animated menu frames & logo PNGs
```

---

## ✧ Execution Flow Overview

When Minecraft boots up with HollowClient installed, it follows this lifecycle flow:

```
[Game Startup]
       │
       ▼
1. MinecraftClientMixin (<init>) ──► Sets clear color to solid black (eliminates white window flash)
       │
       ▼
2. SplashOverlayMixin (render) ───► Intercepts loading screen, draws smooth ease-in-out rotating logo
       │                            on a pitch-black background with timeout protection
       │
       ▼
3. MinecraftClientMixin (setScreen) ► Replaces vanilla "TitleScreen" with HollowHomeScreen
       │
       ▼
4. HollowHomeScreen (render) ─────► Plays the 25fps Red Eye Blink animated background frames
       │                            with custom neon pink styling and drifting particles
       │
       ▼
5. InGameHudMixin (render) ───────► Triggers HollowHudRenderer to paint active modules (Ping, FPS, etc.)
       │                            according to positions defined in HollowHudConfig
```

---

## ✧ Config Directory
Locally, the client configurations are stored in the user's Minecraft runtime instance:
`%appdata%/.minecraft/config/hollowclient/`
- **`config.json`**: Stores basic toggles (AutoClicker CPS limits, Fullbright status, wings cosmetic toggles).
- **`hud_config.json`**: Stores positioning (`x`, `y`), scale (`scale`), and state (`enabled`) for all HUD elements.

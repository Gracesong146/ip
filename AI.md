# AI Assistance Log

**Date:** 17 September 2025  
**Tools:** ChatGPT  
**Scope:** Cathy A-AiAssisted increment

## What I asked for
- *Generate an AI.md template:* "Create an AI.md file template or example for documentation of AI assistance."
- *Heading Generation:* “Add a title "Cathy" to the top of my Cathy GUI window, at native window title bar."
- *Custom Icon:* "JavaFX app’s window and taskbar to show a custom ‘Cathy’ icon instead of the default Duke cup. How do I add the icon to my project and set it in code?"

## What I Implemented
- Set the window title in `Main.java` using `stage.setTitle("Cathy");`.
- Added a custom Cathy icon to `stage.getIcons()` so it shows in the title bar and Windows taskbar.
- Generated a clean, cohesive logo with AI (converted to PNG, placed in `/resources/images/`).
- Rebuilt the app so the GUI now has a personalized look-and-feel with its own name and logo.

## What I Kept / Changed
- Kept existing layout and styling for the chat interface.
- Only minimally edited `Main.java` to set the title and icons — no functional logic changes.
## Why It’s an Improvement
- **Branding:** The app now has a clear identity — “Cathy” — shown in the title bar.
- **Professionalism:** Replacing the default Duke icon with a custom logo gives it a polished, standalone-app feel.
- **User Experience:** Easier for users to locate the app window or taskbar icon at a glance.
- **Consistency:** The custom logo matches the overall GUI theme and creates a cohesive look.
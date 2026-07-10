import os
import re

SRC_DIRS = [
    r"A:\MCMDS\src\main\java\v1_21",
    r"A:\MCMDS\src\main\java\com\cosmeticsmod"
]

replacements = {
    r"VertexFormat\.class_5596": "VertexFormat.DrawMode",
    r"TextRenderer\.class_6415": "TextRenderer.TextLayerType",
    r"VertexConsumerProvider\.class_4598": "VertexConsumerProvider.Immediate",
    r"ColorHelper\.class_5254": "ColorHelper.Argb",
    r"SkinTextures\.class_7920": "SkinTextures.Model",
    r"ResourceReloader\.class_4045": "ResourceReloader.Synchronizer"
}

def fix_inner_classes():
    for src_dir in SRC_DIRS:
        for root, _, files in os.walk(src_dir):
            for file in files:
                if not file.endswith(".java"):
                    continue
                path = os.path.join(root, file)
                with open(path, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                changed = False
                for pattern, repl in replacements.items():
                    if re.search(pattern, content):
                        content = re.sub(pattern, repl, content)
                        changed = True
                
                if changed:
                    with open(path, 'w', encoding='utf-8') as f:
                        f.write(content)
                        
if __name__ == '__main__':
    fix_inner_classes()
    print("Inner classes fixed.")

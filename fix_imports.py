import os
import re

MAPPINGS_FILE = r"C:\Users\minha\.gradle\caches\fabric-loom\1.21.1\net.fabricmc.yarn.1_21_1.1.21.1+build.2-v2\mappings.tiny"
SRC_DIRS = [
    r"A:\MCMDS\src\main\java\v1_21",
    r"A:\MCMDS\src\main\java\com\cosmeticsmod"
]

def load_class_mappings():
    # Maps simple name (e.g. MinecraftClient) to full named package (e.g. net.minecraft.client.MinecraftClient)
    simple_to_full = {}
    
    with open(MAPPINGS_FILE, 'r', encoding='utf-8') as f:
        lines = f.readlines()
        
    for line in lines:
        parts = line.strip('\n').split('\t')
        if line.startswith('c\t'):
            if len(parts) >= 4:
                named_full = parts[3].replace('/', '.')
                simple = named_full.split('.')[-1]
                # Filter out classes that aren't in net.minecraft (like com/mojang etc) if they were obfuscated
                if named_full.startswith("net.minecraft."):
                    simple_to_full[simple] = named_full
                    
    return simple_to_full

def fix_imports(simple_to_full):
    for src_dir in SRC_DIRS:
        for root, _, files in os.walk(src_dir):
            for file in files:
                if not file.endswith(".java"):
                    continue
                path = os.path.join(root, file)
                with open(path, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                # Replace invalid net.minecraft imports
                # "import net.minecraft.MinecraftClient;" -> "import net.minecraft.client.MinecraftClient;"
                def repl_import(m):
                    simple_name = m.group(1)
                    if simple_name in simple_to_full:
                        return f"import {simple_to_full[simple_name]};"
                    return m.group(0)
                
                content = re.sub(r'import\s+net\.minecraft\.([A-Za-z0-9_]+);', repl_import, content)
                
                # Also replace any fully qualified invalid usages like net.minecraft.MinecraftClient
                def repl_fqn(m):
                    simple_name = m.group(1)
                    if simple_name in simple_to_full:
                        return simple_to_full[simple_name]
                    return m.group(0)
                
                content = re.sub(r'net\.minecraft\.([A-Za-z0-9_]+)', repl_fqn, content)
                
                with open(path, 'w', encoding='utf-8') as f:
                    f.write(content)
                    
if __name__ == '__main__':
    print("Loading class mappings...")
    c_map = load_class_mappings()
    print(f"Loaded {len(c_map)} classes.")
    print("Fixing imports...")
    fix_imports(c_map)
    print("Done!")
